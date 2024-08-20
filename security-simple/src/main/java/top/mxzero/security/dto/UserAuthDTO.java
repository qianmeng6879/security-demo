package top.mxzero.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.mxzero.security.entity.UserSession;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/19
 */
@Data
public class UserAuthDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String deviceFlag;
    @NotNull
    private UserSession.DeviceType deviceType;
    private String code;
}
