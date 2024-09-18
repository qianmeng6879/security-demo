package top.mxzero.endpoint.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.dto.RestData;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.endpoint.param.ChangePasswordParam;
import top.mxzero.endpoint.service.UserService;
import top.mxzero.security.jwt.utils.UserProfileUtils;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.rmi.server.ServerCloneException;

/**
 * @author Peng
 * @since 2024/9/18
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/password/change")
    public RestData<?> changePasswordApi(@Valid @RequestBody ChangePasswordParam param) {
        if (!param.getNewPwd().equals(param.getConfirmPwd())) {
            throw new ServiceException("两次密码不一致");
        }
        return RestData.success(this.userService.changePassword(param.getOldPwd(), param.getConfirmPwd(), UserProfileUtils.getId()));
    }
}
