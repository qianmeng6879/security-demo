package top.mxzero.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mxzero.security.config.MinioConfig;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserinfoDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatar;

    public String getAvatarUrl() {
        if (this.avatar != null) {
            if (!this.avatar.startsWith("http")) {
                return MinioConfig.OSS_RESOURCE_PREFIX + this.avatar;
            }
        }
        return this.avatar;
    }
}
