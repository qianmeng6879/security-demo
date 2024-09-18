package top.mxzero.endpoint.service;

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
}
