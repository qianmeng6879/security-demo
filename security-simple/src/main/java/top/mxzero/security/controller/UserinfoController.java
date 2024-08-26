package top.mxzero.security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.controller.params.ChangePasswordParam;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/change_pwd")
    public RestData<Boolean> changePwdApi(Principal principal, @Valid @RequestBody ChangePasswordParam param) {
        return RestData.success(this.memberService.changePassword(principal.getName(), param));
    }
}
