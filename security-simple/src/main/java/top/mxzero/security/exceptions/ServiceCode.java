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
    public static final ServiceCode CLIENT_ERROR = new ServiceCode(1, "客户端参数错误");
    public static final ServiceCode SERVER_ERROR = new ServiceCode(999, "服务端错误");
    public static final ServiceCode PASSWORD_INVALID = new ServiceCode(1001, "用户名或密码错误");
    public static final ServiceCode ACCOUNT_LOCKED = new ServiceCode(1002, "账号被封禁");
    public static final ServiceCode ACCOUNT_2FA = new ServiceCode(1003, "账号需要二次认证");
    public static final ServiceCode EMAIL_INVALID = new ServiceCode(2001, "邮箱格式错误");
    public static final ServiceCode PHONE_INVALID = new ServiceCode(2002, "手机格式错误");
    public static final ServiceCode CODE_REPETITION = new ServiceCode(2005, "验证码重复获取");
}
