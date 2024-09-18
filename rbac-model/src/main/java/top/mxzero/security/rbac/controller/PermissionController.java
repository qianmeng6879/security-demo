package top.mxzero.security.rbac.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.mxzero.common.dto.RestData;
import top.mxzero.security.rbac.entity.Permission;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.util.List;

/**
 * @author Peng
 * @since 2024/9/19
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<List<Permission>> permissionListApi() {
        return RestData.success(this.authorizeService.allPermission(), "权限列表");
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<Boolean> addPermissionApi(@Valid @RequestBody Permission permission) {
        return RestData.success(this.authorizeService.save(permission), "新增权限成功");
    }

    @DeleteMapping("{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<Boolean> removePermissionApi(@PathVariable("id") Long roleId) {
        return RestData.success(this.authorizeService.removePermissionById(roleId), "删除权限");
    }

}
