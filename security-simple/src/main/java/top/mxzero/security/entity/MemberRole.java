package top.mxzero.security.entity;

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
@TableName("t_member_role")
public class MemberRole {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long memberId;
    private Long roleId;

    public MemberRole() {
    }

    public MemberRole(Long memberId, Long roleId) {
        this.memberId = memberId;
        this.roleId = roleId;
    }
}
