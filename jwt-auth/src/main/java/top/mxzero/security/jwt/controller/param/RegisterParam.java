package top.mxzero.security.jwt.controller.param;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Peng
 * @since 2024/9/17
 */
@Data
public class RegisterParam {
    @NotBlank
    @Size(max = 10)
    private String username;
    @NotBlank
    @Size(max = 16)
    private String password;
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;
}
