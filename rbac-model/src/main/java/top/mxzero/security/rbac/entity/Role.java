package top.mxzero.security.rbac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Data
@TableName("t_role")
public class Role {
    @TableId(type = IdType.NONE)
    private Long id;
    private String name;
}
