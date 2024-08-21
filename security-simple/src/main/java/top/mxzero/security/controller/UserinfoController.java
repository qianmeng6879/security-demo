package top.mxzero.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.dto.UserinfoDTO;
import top.mxzero.security.service.MemberService;

import java.security.Principal;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/1
 */
@RestController
public class UserinfoController {
    @Autowired
    private MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/userinfo")
    public Principal userinfoEndpoint(Principal principal) {
       return principal;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/api/userinfo")
    public RestData<UserinfoDTO> userinfoApi(Principal principal) {
        return RestData.success(this.memberService.getUserinfo(principal.getName()));
    }
}
