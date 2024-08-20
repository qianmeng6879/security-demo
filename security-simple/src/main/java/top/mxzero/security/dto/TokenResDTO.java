package top.mxzero.security.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Data
public class TokenResDTO<T> {
    private String token;
    private String twoAuthToken;
    private Map<String, String> items;
}
