package top.mxzero.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.entity.Member;
import top.mxzero.security.mapper.RoleMapper;
import top.mxzero.security.service.AuthorizeService;
import top.mxzero.security.service.MemberService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@RestController
@RequestMapping("/api")
public class AuthorizeController {
    @Autowired
    private RoleMapper mapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping("users/roles")
    public RestData<List<String>> roleListApi(Principal principal) {
        Member member = this.memberService.findByUsername(principal.getName());
        if (member == null) {
            return RestData.success(Collections.emptyList());
        }

        return RestData.success(authorizeService.roleNameByMemberId(member.getId()));
    }

    @GetMapping("users/permissions")
    public RestData<List<String>> permissionsApi(Principal principal) {
        Member member = this.memberService.findByUsername(principal.getName());
        if (member == null) {
            return RestData.success(Collections.emptyList());
        }

        return RestData.success(authorizeService.permissionNameByMember(member.getId()));
    }
}
