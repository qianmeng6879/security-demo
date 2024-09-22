package top.mxzero.security.rbac.service;

import top.mxzero.security.rbac.dto.Userinfo;

/**
 * @author Peng
 * @since 2024/9/18
 */
public interface UserService {
    /**
     * 用户修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @param userId 用户ID
     */
    boolean changePassword(String oldPwd, String newPwd, Long userId);

    /**
     * 获取用户基本信息
     *
     * @param userId 用户ID
     */
    Userinfo getUserinfo(Long userId);

    boolean save(Userinfo userinfo);
}
