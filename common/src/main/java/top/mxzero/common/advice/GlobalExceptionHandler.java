package top.mxzero.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.mxzero.common.dto.RestData;
import top.mxzero.common.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestData<?> handleAllExceptions(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return RestData.error("服务异常", 500);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestData<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            // 获取字段名和错误信息
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errorList.add("【" + fieldName + "】" + errorMessage);
        }

        RestData<Map<String, Object>> result = new RestData<>();
        result.setCode(422);
        result.setError(String.join(",", errorList));
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public RestData<?> handleServiceException(ServiceException e) {
        return RestData.error(e.getMessage(), e.getCode());
    }
}