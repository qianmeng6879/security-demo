package top.mxzero.endpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.jwt.service.impl.JwtService;

/**
 * @author Peng
 * @since 2024/9/17
 */
@RestController
public class MessageController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/message")
    public String echo(String msg) {
        return "【ECHO】" + msg;
    }
}
