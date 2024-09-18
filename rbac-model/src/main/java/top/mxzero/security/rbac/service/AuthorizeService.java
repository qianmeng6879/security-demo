package top.mxzero.security.rbac.service;


import top.mxzero.common.dto.PageDTO;
import top.mxzero.security.rbac.dto.UserDTO;
import top.mxzero.security.rbac.entity.Permission;
import top.mxzero.security.rbac.entity.Role;
import top.mxzero.security.rbac.entity.User;

import java.util.List;

/**
 * 权限类业务
 *
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
public interface AuthorizeService {
    List<String> roleNameByUserId(Long userId);

    List<String> permissionNameByUserId(Long userId);

    List<String> permissionNameByRoleId(Long roleId);

    List<Role> allRole();

    List<Permission> allPermission();

    PageDTO<UserDTO> listUser(long currentPage, long pageSize, String keyword);

    boolean save(Role role);

    boolean save(Permission permission);

    boolean save(User user);

    boolean removeRoleById(Long roleId);

    boolean removePermissionById(Long permissionId);

    boolean removeUserById(Long userId);
}
