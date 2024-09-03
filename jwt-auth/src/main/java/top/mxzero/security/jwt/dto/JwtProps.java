package top.mxzero.security.jwt.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Peng
 * @since 2024/9/3
 */
@Data
@ConfigurationProperties(prefix = "mxzero.security.jwt")
public class JwtProps {
    private String issuer;
    private String secret;
    private long expire;
    private long refresh;
}
