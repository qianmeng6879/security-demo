package top.mxzero.security.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Peng
 * @since 2024/9/3
 */
@RestController
public class UserinfoController {


    @PreAuthorize("isAuthenticated()")
    @RequestMapping("userinfo")
    public Object userinfoApi(Principal principal) {
        return principal;
    }
}

