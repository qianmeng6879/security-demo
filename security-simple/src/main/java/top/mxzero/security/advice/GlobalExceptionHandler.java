package top.mxzero.security.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.exceptions.ServiceCode;
import top.mxzero.security.exceptions.ServiceException;

import java.util.HashMap;
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
    public RestData<?> handleAllException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return RestData.error("系统错误");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public RestData<?> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return RestData.error("用户未登录");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestData<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return RestData.error(e.getRequestURL() + " not found", HttpServletResponse.SC_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestData<?> handleHttpRequestMethodNotSupportedException() {
        return RestData.error("请求方法不支持", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestData<?> handleHttpMessageNotReadableException() {
        return RestData.error("请求体参数错误", HttpStatus.UNPROCESSABLE_ENTITY.value());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestData<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) throws Exception {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, Object> errorMap = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            // 获取字段名和错误信息
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        }

        RestData<Map<String, Object>> result = new RestData<>();
        result.setCode(ServiceCode.CLIENT_ERROR.getCode());
        result.setMessage(new ObjectMapper().writeValueAsString(errorMap));
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public Map<String, Object> handleServiceException(ServiceException e) {
        return Map.of(
                "message", e.getMessage(),
                "code", e.getCode()
        );
    }
}
