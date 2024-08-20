package top.mxzero.security.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.mxzero.security.exceptions.ServiceException;

import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().getName());
        return Map.of("error", "服务错误", "code", 999);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String, Object> exceptionHandler(NoHandlerFoundException e) {
        return Map.of("error", "访问的资源不存在", "code", 999);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public Map<String, Object> handleServiceException(ServiceException e) {
        return Map.of(
                "error", e.getMessage(),
                "code", e.getCode()
        );
    }
}
