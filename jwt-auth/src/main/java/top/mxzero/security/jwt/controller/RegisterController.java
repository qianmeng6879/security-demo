package top.mxzero.security.jwt.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.dto.RestData;
import top.mxzero.security.jwt.controller.param.RegisterParam;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.service.AuthorizeService;

/**
 * @author Peng
 * @since 2024/9/17
 */
@RestController
public class RegisterController {
    @Autowired
    private AuthorizeService authorizeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public RestData<Boolean> registerApi(@Valid @RequestBody RegisterParam param) {
        User user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        user.setNickname(param.getUsername());
        user.setEmail(param.getEmail());
        return RestData.success(authorizeService.save(user));
    }
}
