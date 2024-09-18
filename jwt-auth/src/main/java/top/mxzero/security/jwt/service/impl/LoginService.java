package top.mxzero.security.jwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.security.jwt.controller.param.LoginParam;
import top.mxzero.security.jwt.dto.TokenDTO;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.mapper.UserMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Peng
 * @since 2024/9/19
 */
@Service
public class LoginService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public TokenDTO login(LoginParam param) {
        User user = userMapper.findByUsername(param.getUsername());
        if (user == null || !this.passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        userMapper.updateLoginTimeById(user.getId(), new Date());
        Map<String, Object> subject = new HashMap<>();
        subject.put("id", user.getId());
        subject.put("username", user.getUsername());
        try {
            return jwtService.createAccessTokenAndRefreshToken(UUID.randomUUID().toString(), UUID.randomUUID().toString(), objectMapper.writeValueAsString(subject));
        } catch (Exception ignore) {
            return null;
        }
    }
}
