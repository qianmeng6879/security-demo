package top.mxzero.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@Data
@NoArgsConstructor
public class UserSession {
    @TableId(type = IdType.NONE)
    private String sessionId;
    private String name;
    private Date createdAt;
    private Date lastAccessAt;
    private String deviceType;
    private String token;
    private Long expire;
    private String deviceFlag;

    @Getter
    public enum DeviceType {
        WEB("web"), PHONE("phone");
        private final String value;

        DeviceType(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }

    }
}
