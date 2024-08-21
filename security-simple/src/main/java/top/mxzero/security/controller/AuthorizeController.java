package top.mxzero.security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.dto.RoleDTO;
import top.mxzero.security.entity.Member;
import top.mxzero.security.entity.Permission;
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


    @GetMapping("roles")
    public RestData<List<RoleDTO>> roleListApi() {
        return RestData.success(authorizeService.getAllRole());
    }

    @PostMapping("roles")
    public RestData<Boolean> roleAddApi(@Valid @RequestBody RoleDTO dto) {
        return RestData.success(authorizeService.save(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("roles/{id:\\d+}")
    public RestData<Boolean> removeRoleApi(@PathVariable("id") Long id) {
        return RestData.success(authorizeService.removeRoleById(id));
    }


    @GetMapping("permissions")
    public RestData<List<Permission>> permissionListApi() {
        return RestData.success(authorizeService.getAllPermission());
    }

    @PostMapping("permissions")
    public RestData<Boolean> permissionSaveApi(@Valid @RequestBody Permission permission) {
        return RestData.success(authorizeService.save(permission));
    }

    @DeleteMapping("permissions/{id:\\d+}")
    public RestData<Boolean> permissionRemoveApi(@PathVariable("id") Long permissionId) {
        return RestData.success(authorizeService.removePermissionById(permissionId));
    }

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
