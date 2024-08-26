package top.mxzero.security.controller.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.mxzero.security.validate.FieldMatch;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/26
 */
@Data
@FieldMatch(first = "newPwd", second = "confirmPwd")
public class ChangePasswordParam {
    @NotBlank
    @Length(max = 16)
    private String oldPwd;

    @NotBlank
    @Length(max = 16)
    private String newPwd;

    @NotBlank
    @Length(max = 16)
    private String confirmPwd;
}
