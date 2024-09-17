package top.mxzero.security.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.security.rbac.entity.Role;
import top.mxzero.security.rbac.entity.RolePermission;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.entity.UserRole;
import top.mxzero.security.rbac.mapper.*;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.security.Permission;
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
        return List.of();
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
        return List.of();
    }

    @Override
    public List<Permission> allPermission() {
        return List.of();
    }

    @Override
    public boolean save(Role role) {
        return false;
    }

    @Override
    public boolean save(Permission permission) {
        return false;
    }

    @Override
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
