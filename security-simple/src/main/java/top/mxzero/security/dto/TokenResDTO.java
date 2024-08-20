package top.mxzero.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.mxzero.security.exceptions.ServiceCode;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Data
@AllArgsConstructor
public class TokenResDTO<T> {
    private T data;
    private ServiceCode code;
}
