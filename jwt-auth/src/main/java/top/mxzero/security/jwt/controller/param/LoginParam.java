package top.mxzero.security.jwt.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Data
public class LoginParam {
    @NotBlank
    @Size(max = 10)
    private String username;
    @NotBlank
    @Size(max = 16)
    private String password;
}
