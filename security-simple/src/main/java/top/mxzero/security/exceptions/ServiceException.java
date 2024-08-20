package top.mxzero.security.exceptions;

import lombok.Getter;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Getter
public class ServiceException extends RuntimeException {
    private final int code;

    public ServiceException(String message) {
        this(message, 1);
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(ServiceCode exceptionsCode) {
        this(exceptionsCode.getMessage(), exceptionsCode.getCode());
    }

}
