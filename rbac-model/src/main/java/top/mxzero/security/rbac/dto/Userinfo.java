package top.mxzero.security.rbac.dto;

import lombok.Data;

/**
 * @author Peng
 * @since 2024/9/19
 */
@Data
public class Userinfo {
    private Long id;
    private String username;
    private String avatarUrl;
}