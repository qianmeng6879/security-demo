package top.mxzero.security.rbac.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Peng
 * @since 2024/9/19
 */
@Data
public class Userinfo {
    private Long id;
    private String username;
    @Length(min = 2, max = 10)
    private String nickname;
    @Length(max = 255)
    @Pattern(regexp = "^https?://.*?\\.(png|jpg|jpeg|gif)$")
    private String avatarUrl;
}