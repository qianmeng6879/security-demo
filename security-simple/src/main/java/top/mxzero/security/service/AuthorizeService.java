package top.mxzero.security.service;

import top.mxzero.security.entity.Permission;
import top.mxzero.security.entity.Role;

import java.util.List;

/**
 * 权限类业务
 *
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
public interface AuthorizeService {
    /**
     * 获取角色字符串
     *
     * @param memberId 用户ID
     */
    List<String> roleNameByMemberId(Long memberId);

    /**
     * 获取权限字符串
     *
     * @param memberId 用户ID
     */
    List<String> permissionNameByMember(Long memberId);

    /**
     * 获取全部角色数据
     */
    List<Role> getAllRole();

    /**
     * 获取全部权限数据
     */
    List<Permission> getAllPermission();

    /**
     * 保存角色信息
     */
    boolean save(Role role);

    /**
     * 保存权限信息
     */
    boolean save(Permission permission);
}
