package top.mxzero.security.rbac.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.mxzero.common.dto.RestData;
import top.mxzero.security.rbac.entity.Role;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.util.List;

/**
 * @author Peng
 * @since 2024/9/18
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<List<Role>> roleListApi() {
        return RestData.success(this.authorizeService.allRole(), "角色列表");
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<Boolean> addRoleApi(@Valid @RequestBody Role role) {
        return RestData.success(this.authorizeService.save(role), "新增角色成功");
    }

    @DeleteMapping("{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<Boolean> removeRoleApi(@PathVariable("id") Long roleId) {
        return RestData.success(this.authorizeService.removeRoleById(roleId), "删除角色");
    }
}
