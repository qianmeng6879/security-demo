package top.mxzero.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/1
 */
@RestController
public class UserinfoController {
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/userinfo")
    public Object userinfoApi(Principal principal) {
        return principal.getName();
    }
}
