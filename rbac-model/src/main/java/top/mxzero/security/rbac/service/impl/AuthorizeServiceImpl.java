package top.mxzero.security.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.mxzero.common.dto.PageDTO;
import top.mxzero.common.dto.UserProfile;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.common.utils.DeepBeanUtil;
import top.mxzero.security.rbac.dto.UserDTO;
import top.mxzero.security.rbac.entity.*;
import top.mxzero.security.rbac.mapper.*;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 * @since 2024/9/13
 */
@Slf4j
public class AuthorizeServiceImpl implements AuthorizeService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserProfile getUserProfile(String username) {
//        String key = USERINFO_KEY_PREFIX + username;
//        String data = this.redisTemplate.opsForValue().get(key);
//
//        if (StringUtils.hasLength(data)) {
//            try {
//                Map<String, Object> map = this.objectMapper.readValue(data, Map.class);
//                new UserProfile(map.get("id"), map.get("username"), map.get("password"), map.get(""));
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        }


        User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new ServiceException("用户【" + username + "】不存在");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>(this.roleNameByUserId(user.getId()).stream().map(SimpleGrantedAuthority::new).toList());
        authorities.addAll(this.permissionNameByUserId(user.getId()).stream().map(SimpleGrantedAuthority::new).toList());
        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getPassword(), authorities);
//        try {
//            this.redisTemplate.opsForValue().set(key, this.objectMapper.writeValueAsString(user), 30, TimeUnit.MINUTES);
//        } catch (Exception ignored) {
//        }
        return userProfile;
    }

    @Override
    public List<String> roleNameByUserId(Long userId) {
        List<Long> roleIds = this.userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id")).stream().map(UserRole::getRoleId).toList();
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMapper.selectList(new QueryWrapper<Role>().in("id", roleIds).select("name")).stream().map(Role::getName).toList();
    }

    @Override
    public List<String> permissionNameByUserId(Long userId) {
        return permissionMapper.findNameByUserId(userId);
    }

    @Override
    public List<String> permissionNameByRoleId(Long roleId) {
        List<Long> permissionIds = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", roleId).select("permission_id")).stream().map(RolePermission::getPermissionId).toList();
        if (permissionIds.isEmpty()) {
            return Collections.emptyList();
        }
        return this.permissionMapper.selectList(new QueryWrapper<Permission>().in("id", permissionIds).select("name")).stream().map(Permission::getName).toList();
    }

    @Override
    public List<Role> allRole() {
        return this.roleMapper.selectList(null);
    }

    @Override
    public List<Permission> allPermission() {
        return this.permissionMapper.selectList(null);
    }

    @Override
    public PageDTO<UserDTO> listUser(long currentPage, long pageSize, String keyword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("deleted", 0);

        if (StringUtils.hasLength(keyword)) {
            queryWrapper.like("username", keyword).or(or -> or.like("nickname", keyword)).or(or -> or.like("email", keyword)).or(or -> or.like("phone", keyword));
        }

        Page<User> page = userMapper.selectPage(new Page<User>(currentPage, pageSize), queryWrapper);

        return new PageDTO<>(DeepBeanUtil.copyProperties(page.getRecords(), UserDTO.class), page.getTotal(), pageSize, currentPage);
    }

    @Override
    @Transactional
    public boolean save(Role role) {
        if (this.roleMapper.exists(new QueryWrapper<Role>().eq("name", role.getName()).ne("id", role.getId()))) {
            throw new ServiceException("权限已存在");
        }
        boolean result = roleMapper.insertOrUpdate(role);
        // 角色原有权限
        List<Long> originPermissionIds = permissionMapper.findByRoleId(role.getId()).stream().map(Permission::getId).toList();

        // 获取需要删除权限
        List<Long> perRemovePermissions = originPermissionIds.stream().filter(origin -> !role.getPermissionIds().contains(origin)).toList();

        // 获取需要增加的权限
        List<Long> perAddPermissions = role.getPermissionIds().stream().filter(item -> !originPermissionIds.contains(item)).toList();

        if (result) {
            if (!perRemovePermissions.isEmpty()) {
                rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", role.getId()).in("permission_id", perRemovePermissions));
            }
            if (!perAddPermissions.isEmpty()) {
                List<RolePermission> addPermissions = perAddPermissions.stream().map(item -> new RolePermission(IdWorker.getId(), role.getId(), item)).toList();
                rolePermissionMapper.insertBatch(addPermissions);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public boolean save(Permission permission) {
        if (this.permissionMapper.exists(new QueryWrapper<Permission>().eq("name", permission.getName()).ne("id", permission.getId()))) {
            throw new ServiceException("权限已存在");
        }
        return this.permissionMapper.insertOrUpdate(permission);
    }

    @Override
    @Transactional
    public boolean save(User user) {
        if (userMapper.exists(new QueryWrapper<User>().eq("username", user.getUsername()).eq("deleted", 0))) {
            throw new ServiceException("用户名已存在");
        }

        if (user.getEmail() != null && userMapper.exists(new QueryWrapper<User>().eq("email", user.getEmail()).eq("deleted", 0))) {
            throw new ServiceException("邮箱已被注册");
        }


        if (user.getPhone() != null && userMapper.exists(new QueryWrapper<User>().eq("phone", user.getEmail()).eq("deleted", 0))) {
            throw new ServiceException("手机号已被注册");
        }

        if (Boolean.TRUE.equals(this.redisTemplate.hasKey(USERINFO_KEY_PREFIX + user.getUsername()))) {
            this.redisTemplate.delete(USERINFO_KEY_PREFIX + user.getUsername());
        }


        return userMapper.insert(user) > 0;
    }

    @Override
    @Transactional
    public boolean removeRoleById(Long roleId) {
        boolean result = roleMapper.deleteById(roleId) > 0;
        if (result) {
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("role_id", roleId));
            rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        }
        return result;
    }

    @Override
    @Transactional
    public boolean removePermissionById(Long permissionId) {
        boolean result = permissionMapper.deleteById(permissionId) > 0;
        if (result) {
            rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("permission_id", permissionId));
        }
        return result;
    }

    @Override
    @Transactional
    public boolean removeUserById(Long userId) {
        boolean result = permissionMapper.deleteById(userId) > 0;
        if (result) {
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userId));
        }
        return result;
    }
}
