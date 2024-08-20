package top.mxzero.security.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Getter
@AllArgsConstructor
public final class ServiceCode {
    private final int code;
    private final String message;

    public static final ServiceCode SUCCESS = new ServiceCode(0, "响应正常");
    public static final ServiceCode PASSWORD_INVALID = new ServiceCode(100, "用户名或密码错误");
    public static final ServiceCode ACCOUNT_LOCKED = new ServiceCode(101, "账号被封禁");
    public static final ServiceCode ACCOUNT_2FA = new ServiceCode(102, "账号需要二次认证");
}
