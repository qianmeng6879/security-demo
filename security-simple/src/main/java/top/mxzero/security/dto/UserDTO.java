package top.mxzero.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends UserinfoDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date LastLoginTime;
    private Integer isSuperuser;
    private Integer enable2fa;
    private Integer locked;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
