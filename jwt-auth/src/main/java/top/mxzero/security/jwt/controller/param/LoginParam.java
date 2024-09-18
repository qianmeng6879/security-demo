package top.mxzero.security.jwt.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Data
public class LoginParam {
    @NotBlank
    @Length(max = 10)
    private String username;
    @NotBlank
    @Length(min = 6, max = 16)
    private String password;
}
