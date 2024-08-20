package top.mxzero.security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.dto.ApiResponse;
import top.mxzero.security.dto.TokenResDTO;
import top.mxzero.security.dto.UserAuthDTO;
import top.mxzero.security.service.TokenService;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public ApiResponse<?> createTokenApi(@Valid @RequestBody UserAuthDTO dto) {
        TokenResDTO<?> tokenRes = tokenService.createToken(dto);
        return ApiResponse.success(tokenRes.getData(), tokenRes.getCode());
    }
}
