package top.mxzero.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.controller.params.PageAndSearch;
import top.mxzero.security.dto.PageDTO;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.dto.UserDTO;
import top.mxzero.security.service.MemberService;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private MemberService memberService;

    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    @GetMapping
    public RestData<PageDTO<UserDTO>> userListApi(PageAndSearch params) {
        return RestData.success(memberService.findPage(params));
    }

}
