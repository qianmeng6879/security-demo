package top.mxzero.security.jwt.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.dto.RestData;
import top.mxzero.security.jwt.controller.param.LoginParam;
import top.mxzero.security.jwt.dto.TokenDTO;
import top.mxzero.security.jwt.service.impl.TokenService;

/**
 * @author Peng
 * @since 2024/9/3
 */
@Slf4j
@RestController
public class TokenController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public RestData<TokenDTO> tokenApi(@Valid @RequestBody LoginParam loginParam) {
        return RestData.success(tokenService.login(loginParam));
    }

    @PostMapping("/token/refresh")
    public RestData<TokenDTO> refreshTokenApi(@RequestParam String token) {
        return RestData.success(tokenService.refreshToken(token));
    }
}
