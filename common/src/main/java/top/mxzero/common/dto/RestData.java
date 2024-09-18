package top.mxzero.common.dto;

import lombok.Data;

/**
 * @author Zhang Peng
 * @email qianmeng6879@163.com
 * @since 2023/9/1
 */
@Data
public class RestData<T> {
    public static final int DEFAULT_ERROR_CODE = 999;
    public static final int DEFAULT_SUCCESS_CODE = 0;

    private String message;
    private Object error;
    private T data;
    private int code;


    public static <T> RestData<T> success(T data) {
        return success(data, "success");
    }

    public static <T> RestData<T> success(T data, String message) {
        RestData<T> restData = new RestData<>();
        restData.setData(data);
        restData.setMessage(message);
        restData.setCode(DEFAULT_SUCCESS_CODE);
        return restData;
    }

    public static RestData<?> error(Object errMsg) {
        return error(errMsg, DEFAULT_ERROR_CODE);
    }

    public static RestData<?> error(Object errMsg, int errorCode) {
        RestData<?> restData = new RestData<>();
        restData.setError(errMsg);
        restData.setCode(errorCode);
        return restData;
    }

}
