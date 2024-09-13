package top.mxzero.security.rbac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private Integer deleted;
}
