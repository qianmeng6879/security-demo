package top.mxzero.endpoint.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Data
public class ChangePasswordParam {
    @NotBlank
    @Length(min = 6, max = 16)
    private String oldPwd;
    @NotBlank
    @Length(min = 6, max = 16)
    private String newPwd;
    @NotBlank
    @Length(min = 6, max = 16)
    private String confirmPwd;
}
