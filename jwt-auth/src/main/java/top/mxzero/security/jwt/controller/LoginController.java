package top.mxzero.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.jwt.service.impl.JwtService;

import java.util.UUID;

/**
 * @author Peng
 * @since 2024/9/3
 */
@RestController
public class LoginController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @RequestMapping("/login")
    public Object loginApi(String username, String password) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            return "no";
        }
        return jwtService.createAccessTokenAndRefreshToken(UUID.randomUUID().toString(), UUID.randomUUID().toString(), userDetails.getUsername());
    }
}
