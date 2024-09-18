package top.mxzero.security.rbac.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Peng
 * @since 2024/9/19
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatarUrl;
    private Date lastLoginTime;
    private Date createdTime;
    private Date updatedTime;
    private List<String> roles;
}
