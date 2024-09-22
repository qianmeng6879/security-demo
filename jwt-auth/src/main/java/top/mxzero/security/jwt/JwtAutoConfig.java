package top.mxzero.security.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.mxzero.security.jwt.controller.TokenController;
import top.mxzero.security.jwt.dto.JwtProps;
import top.mxzero.security.jwt.service.impl.JwtService;
import top.mxzero.security.jwt.service.impl.TokenService;

/**
 * @author Peng
 * @since 2024/9/3
 */
@Import({JwtService.class , TokenService.class, TokenController.class})
@Configuration
@EnableConfigurationProperties(JwtProps.class)
public class JwtAutoConfig {
}
