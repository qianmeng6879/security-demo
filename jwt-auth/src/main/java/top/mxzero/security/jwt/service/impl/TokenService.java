package top.mxzero.security.jwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.common.exceptions.ServiceException;
import top.mxzero.security.jwt.controller.param.LoginParam;
import top.mxzero.security.jwt.dto.TokenDTO;
import top.mxzero.security.jwt.dto.TokenType;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.mapper.UserMapper;

import java.util.*;

/**
 * @author Peng
 * @since 2024/9/19
 */
public class TokenService {
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

    public TokenDTO refreshToken(String token) {
        if (jwtService.verifyToken(token)) {
            Jws<Claims> claimsJws = jwtService.parseToken(token);
            Object type = claimsJws.getBody().get("token_type");
            if (Objects.equals(type, TokenType.REFRESH_TOKEN.getValue())) {
                String refreshToken = token;
                if ((claimsJws.getBody().getExpiration().getTime() / 3) * 1000 < jwtService.getRefreshExpire()) {
                    refreshToken = jwtService.createToken(UUID.randomUUID().toString(), claimsJws.getBody().getSubject(), TokenType.REFRESH_TOKEN);
                }
                return new TokenDTO(jwtService.createToken(UUID.randomUUID().toString(), claimsJws.getBody().getSubject()), refreshToken, jwtService.getAccessExpire());
            }
            throw new ServiceException("仅允许使用refresh_token");
        }
        throw new ServiceException("token无效");
    }
}
