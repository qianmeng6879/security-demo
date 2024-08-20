package top.mxzero.security.dto;

import lombok.Data;
import top.mxzero.security.exceptions.ServiceCode;

import java.io.Serializable;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Data
public class RestData<T> implements Serializable {
    private String message;
    private int code;
    private T data = null;



    public static <T> RestData<T> success(T data) {
        return success(data, "success");
    }

    public static <T> RestData<T> success(T data, String message) {
        return success(data, message, ServiceCode.SUCCESS);
    }

    public static <T> RestData<T> success(T data, ServiceCode code) {
        return success(data, "success", code);
    }

    public static <T> RestData<T> success(T data, String message, ServiceCode code) {
        RestData<T> response = new RestData<>();
        response.setCode(code.getCode());
        response.setData(data);
        response.setMessage(message);
        return response;
    }


    public static RestData<?> error(String error) {
        return error(error, ServiceCode.CLIENT_ERROR.getCode());
    }

    public static RestData<?> error(ServiceCode code) {
        return error(code.getMessage(), code.getCode());
    }

    public static RestData<?> error(String error, int code) {
        RestData<?> response = new RestData<>();
        response.setCode(code);
        response.setMessage(error);
        return response;
    }
}
