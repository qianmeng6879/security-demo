package top.mxzero.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.utils.UserProfileUtils;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @since 2024/9/3
 */
@RestController
public class UserinfoController {
    @Autowired
    private AuthorizeService authorizeService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/userinfo")
    public Object userinfoApi(Principal principal) {
        List<String> roleList = this.authorizeService.roleNameByUserId(UserProfileUtils.getId());
        List<String> permissionList = this.authorizeService.permissionNameByUserId(UserProfileUtils.getId());
        return Map.of(
                "id", UserProfileUtils.getId(),
                "name", principal.getName(),
                "roles", roleList,
                "permissions", permissionList
        );
    }
}

