package top.mxzero.security.jwt.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.dto.RestData;
import top.mxzero.security.jwt.controller.param.LoginParam;
import top.mxzero.security.jwt.service.impl.JwtService;

import java.util.UUID;

/**
 * @author Peng
 * @since 2024/9/3
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/token")
    public RestData<?> loginApi(@Valid @RequestBody LoginParam loginParam, HttpServletResponse response) {
        try {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginParam.getUsername());
            if (!passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return RestData.error("用户名或密码错误");
            }
            return RestData.success(jwtService.createAccessTokenAndRefreshToken(UUID.randomUUID().toString(), UUID.randomUUID().toString(), userDetails.getUsername()));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestData.error("用户名或密码错误");
        }
    }
}
