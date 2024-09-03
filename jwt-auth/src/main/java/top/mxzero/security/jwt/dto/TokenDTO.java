package top.mxzero.security.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhang Peng
 * @email qianmeng6879@163.com
 * @since 2023/9/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    @JsonProperty("access_token")
    private String access;
    @JsonProperty("refresh_token")
    private String refresh;
    private long expire;
}
