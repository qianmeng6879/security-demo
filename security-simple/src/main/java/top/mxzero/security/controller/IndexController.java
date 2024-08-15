package top.mxzero.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/1
 */
@Controller
public class IndexController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/admin")
    @ResponseBody
    public String adminRoleApi(Principal principal) {
        return principal.getName();
    }
}
