package top.mxzero.endpoint.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Peng
 * @since 2024/9/21
 */
@Data
@TableName("t_message")
public class Message {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    @JsonProperty("created_time")
    private Date createdTime;
}
