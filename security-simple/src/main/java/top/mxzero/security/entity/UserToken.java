package top.mxzero.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/19
 */
@Data
public class UserToken {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private String username;
    private String token;
    private Date createdAt;
    private Date lastAccessAt;
    private String deviceType;
    private Long expire;
    private String deviceFlag;
    private Integer state;

    @Getter
    @AllArgsConstructor
    public static enum TokenState {
        CANCEL(0), // 被取消的token
        NORMAL(1), // 正常token
        KICK(2), // 被另一设备踢下线
        EXPIRE(3); // token过期
        private final Integer state;
    }
}
