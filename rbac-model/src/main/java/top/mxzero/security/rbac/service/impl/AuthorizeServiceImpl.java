package top.mxzero.security.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.security.rbac.entity.*;
import top.mxzero.security.rbac.mapper.*;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.util.Collections;
import java.util.List;

/**
 * @author Peng
 * @since 2024/9/13
 */
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
        return List.of();
    }

    @Override
    public List<String> permissionNameByRoleId(Long roleId) {
        return List.of();
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
        if (userMapper.exists(new QueryWrapper<User>().eq("username", user.getUsername()).eq("deleted", 0).ne("id", user.getId()))) {
            throw new ServiceException("用户名已存在");
        }

        if (user.getEmail() != null && userMapper.exists(new QueryWrapper<User>().eq("email", user.getEmail()).eq("deleted", 0).ne("id", user.getId()))) {
            throw new ServiceException("邮箱已被注册");
        }


        if (user.getPhone() != null && userMapper.exists(new QueryWrapper<User>().eq("phone", user.getEmail()).eq("deleted", 0).ne("id", user.getId()))) {
            throw new ServiceException("手机号已被注册");
        }

        return userMapper.insertOrUpdate(user);
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
