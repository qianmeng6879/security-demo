package top.mxzero.security.rbac.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.mxzero.common.dto.PageDTO;
import top.mxzero.common.dto.RestData;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.common.utils.UserProfileUtils;
import top.mxzero.security.rbac.dto.UserDTO;
import top.mxzero.security.rbac.param.ChangePasswordParam;
import top.mxzero.security.rbac.service.AuthorizeService;
import top.mxzero.security.rbac.service.UserService;

/**
 * @author Peng
 * @since 2024/9/19
 */
@RestController
public class UserController {
    @Autowired
    private AuthorizeService authorizeService;

    @Autowired
    private UserService userService;

    @PostMapping("/users/password/change")
    public RestData<?> changePasswordApi(@Valid @RequestBody ChangePasswordParam param) {
        if (!param.getNewPwd().equals(param.getConfirmPwd())) {
            throw new ServiceException("两次密码不一致");
        }
        return RestData.success(this.userService.changePassword(param.getOldPwd(), param.getConfirmPwd(), UserProfileUtils.getId()));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<PageDTO<UserDTO>> userPageApi(
            @RequestParam(value = "page", defaultValue = "1") long page,
            @RequestParam(value = "size", defaultValue = "10") long size,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return RestData.success(authorizeService.listUser(page, size, keyword));
    }


}
