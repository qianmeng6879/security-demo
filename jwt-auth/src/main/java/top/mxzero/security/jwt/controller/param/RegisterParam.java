package top.mxzero.security.jwt.controller.param;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Peng
 * @since 2024/9/17
 */
@Data
public class RegisterParam {
    @NotBlank
    @Length(max = 10)
    private String username;
    @NotBlank
    @Length(min = 6, max = 16)
    private String password;
    @Email
    @NotBlank
    @Length(max = 100)
    private String email;
}
