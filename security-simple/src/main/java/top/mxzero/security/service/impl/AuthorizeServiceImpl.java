package top.mxzero.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.security.dto.RoleDTO;
import top.mxzero.security.entity.MemberRole;
import top.mxzero.security.entity.Permission;
import top.mxzero.security.entity.Role;
import top.mxzero.security.entity.RolePermission;
import top.mxzero.security.exceptions.ServiceCode;
import top.mxzero.security.exceptions.ServiceException;
import top.mxzero.security.mapper.MemberRoleMapper;
import top.mxzero.security.mapper.PermissionMapper;
import top.mxzero.security.mapper.RoleMapper;
import top.mxzero.security.mapper.RolePermissionMapper;
import top.mxzero.security.service.AuthorizeService;
import top.mxzero.security.utils.DeepBeanUtil;

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
    public List<RoleDTO> getAllRole() {
        List<Role> roles = roleMapper.selectList(null);
        List<RoleDTO> roleDTOList = DeepBeanUtil.copyProperties(roles, RoleDTO::new);

        // 获取角色关联的权限ID
        roleDTOList.forEach(roleDTO -> {
            List<String> permissionIds = permissionMapper.findByRoleId(roleDTO.getId()).stream().map(permission -> permission.getId().toString()).toList();
            roleDTO.setPermissionIds(permissionIds);
        });

        return roleDTOList;
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.selectList(null);
    }

    @Override
    @Transactional
    public boolean save(RoleDTO dto) {
        Role role = DeepBeanUtil.copyProperties(dto, Role::new);
        if (role.getId() != null) {
            // 判断新的角色名称与其他角色不重名
            if (roleMapper.exists(new QueryWrapper<Role>().eq("name", dto.getName()).ne("id", dto.getId()))) {
                throw new ServiceException("角色【" + dto.getName() + "】已存在", ServiceCode.CLIENT_ERROR.getCode());
            }

            // 角色原有权限
            List<Long> originPermissionIds = permissionMapper.findByRoleId(dto.getId()).stream().map(Permission::getId).toList();

            // 获取需要删除权限
            List<Long> perRemovePermissions = originPermissionIds.stream().filter(origin -> !dto.getPermissionIds().contains(origin.toString())).toList();

            // 获取需要增加的权限
            List<Long> perAddPermissions = dto.getPermissionIds().stream().filter(item -> !originPermissionIds.contains(Long.parseLong(item))).map(Long::parseLong).toList();


            boolean result = roleMapper.updateById(role) > 0;
            if (result) {
                if (!perRemovePermissions.isEmpty()) {
                    rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", role.getId()).in("permission_id", perRemovePermissions));
                }
                if (!perAddPermissions.isEmpty()) {
                    List<RolePermission> addPermissions = perAddPermissions.stream().map(item -> new RolePermission(IdWorker.getId(), role.getId(), item)).toList();
                    rolePermissionMapper.insertBatch(addPermissions);
                }
            }

            // 更新以后的权限
            return result;
        }

        // 判断角色名称是否存在
        if (roleMapper.exists(new QueryWrapper<Role>().eq("name", role.getName()))) {
            throw new ServiceException("角色【" + dto.getName() + "】已存在", ServiceCode.CLIENT_ERROR.getCode());
        }

        roleMapper.insert(role);
        List<RolePermission> rolePermissions = dto.getPermissionIds().stream().map(item -> new RolePermission(IdWorker.getId(), role.getId(), Long.parseLong(item))).toList();
        rolePermissionMapper.insertBatch(rolePermissions);
        return true;
    }

    @Override
    @Transactional
    public boolean removeRoleById(Long roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            return false;
        }

        if (role.getReadonly() == 1) {
            throw new ServiceException("该角色不允许被删除", ServiceCode.CLIENT_ERROR.getCode());
        }
        boolean result = roleMapper.deleteById(roleId) > 0;
        if (result) {
            // 删除用户与角色绑定关系
            memberRoleMapper.delete(new QueryWrapper<MemberRole>().eq("role_id", roleId));

            // 删除角色和权限绑定关系
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
    public boolean save(Permission permission) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", permission.getName());
        if (permission.getId() != null) {
            queryWrapper.le("id", permission.getId());
            if (permissionMapper.exists(queryWrapper)) {
                throw new ServiceException("权限信息已存在", ServiceCode.CLIENT_ERROR.getCode());
            }
            return permissionMapper.updateById(permission) > 0;
        }

        if (permissionMapper.exists(queryWrapper)) {
            throw new ServiceException("权限信息已存在", ServiceCode.CLIENT_ERROR.getCode());
        }

        return permissionMapper.insert(permission) > 0;
    }
}
