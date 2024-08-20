package top.mxzero.security.dto;

import lombok.Data;
import top.mxzero.security.exceptions.ServiceCode;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Data
public class ApiResponse<T> {
    private String message;
    private String error;
    private int code;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "success");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return success(data, message, ServiceCode.SUCCESS);
    }

    public static <T> ApiResponse<T> success(T data, ServiceCode code) {
        return success(data, "success", code);
    }

    public static <T> ApiResponse<T> success(T data, String message, ServiceCode code) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code.getCode());
        response.setData(data);
        response.setMessage(message);
        return response;
    }
}
