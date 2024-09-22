package top.mxzero.security.rbac.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.mxzero.common.dto.PageDTO;
import top.mxzero.common.dto.RestData;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.common.utils.UserProfileUtils;
import top.mxzero.security.rbac.dto.UserDTO;
import top.mxzero.security.rbac.dto.Userinfo;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.param.ChangePasswordParam;
import top.mxzero.security.rbac.param.RegisterParam;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users/password/change")
    public RestData<?> changePasswordApi(@Valid @RequestBody ChangePasswordParam param) {
        if (!param.getNewPwd().equals(param.getConfirmPwd())) {
            throw new ServiceException("两次密码不一致");
        }
        return RestData.success(this.userService.changePassword(param.getOldPwd(), param.getConfirmPwd(), UserProfileUtils.getId()), "修改密码");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestData<PageDTO<UserDTO>> userPageApi(
            @RequestParam(value = "page", defaultValue = "1") long page,
            @RequestParam(value = "size", defaultValue = "10") long size,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return RestData.success(authorizeService.listUser(page, size, keyword), "用户列表");
    }


    @GetMapping("/userinfo")
    public RestData<Userinfo> userinfoApi() {
        return RestData.success(userService.getUserinfo(UserProfileUtils.getId()), "用户基本信息");
    }

    @PostMapping("/userinfo/update")
    public RestData<Boolean> userinfoUpdateApi(@Valid @RequestBody Userinfo userinfo) {
        userinfo.setUsername(null);
        userinfo.setId(UserProfileUtils.getId());
        return RestData.success(userService.save(userinfo), "修改用户基本信息");
    }

    @PostMapping("/register")
    public RestData<Boolean> registerApi(@Valid @RequestBody RegisterParam param) {
        User user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        user.setNickname(param.getUsername());
        user.setEmail(param.getEmail());
        return RestData.success(authorizeService.save(user), "用户注册");
    }

}
