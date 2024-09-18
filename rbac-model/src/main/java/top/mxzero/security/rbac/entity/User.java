package top.mxzero.security.rbac.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Peng
 * @since 2024/9/5
 */
@TableName("t_user")
@Data
public class User {
    @TableId(type = IdType.NONE)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String avatarUrl;
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
    private Date lastLoginTime;
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<Long> roleIds;
}
