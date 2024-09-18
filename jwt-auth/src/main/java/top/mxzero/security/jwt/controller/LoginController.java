package top.mxzero.security.jwt.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.dto.RestData;
import top.mxzero.security.jwt.controller.param.LoginParam;
import top.mxzero.security.jwt.dto.TokenDTO;
import top.mxzero.security.jwt.service.impl.LoginService;

/**
 * @author Peng
 * @since 2024/9/3
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginService loginService;

    @PostMapping("/token")
    public RestData<TokenDTO> loginApi(@Valid @RequestBody LoginParam loginParam) {
        return RestData.success(loginService.login(loginParam));
    }
}
