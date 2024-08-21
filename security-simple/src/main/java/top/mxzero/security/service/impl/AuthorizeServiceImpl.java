package top.mxzero.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.security.entity.MemberRole;
import top.mxzero.security.entity.Permission;
import top.mxzero.security.entity.Role;
import top.mxzero.security.mapper.MemberRoleMapper;
import top.mxzero.security.mapper.PermissionMapper;
import top.mxzero.security.mapper.RoleMapper;
import top.mxzero.security.mapper.RolePermissionMapper;
import top.mxzero.security.service.AuthorizeService;

import java.util.Collections;
import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private MemberRoleMapper memberRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> roleNameByMemberId(Long memberId) {
        List<Long> roleIds = memberRoleMapper.selectList(new QueryWrapper<MemberRole>().eq("member_id", memberId).select("role_id")).stream().map(MemberRole::getRoleId).toList();
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMapper.selectList(new QueryWrapper<Role>().in("id", roleIds).select("name")).stream().map(Role::getName).toList();
    }

    @Override
    public List<String> permissionNameByMember(Long memberId) {
        return roleMapper.findNameByMemberId(memberId);
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.selectList(null);
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.selectList(null);
    }

    @Override
    public boolean save(Role role) {
        if (role.getId() != null) {
            return roleMapper.updateById(role) > 0;
        }
        return roleMapper.insert(role) > 0;
    }

    @Override
    public boolean save(Permission permission) {
        if (permission.getId() != null) {
            return permissionMapper.updateById(permission) > 0;
        }
        return permissionMapper.insert(permission) > 0;
    }
}
