package top.mxzero.security.exceptions;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
