package top.mxzero.security.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.mxzero.security.rbac.service.AuthorizeService;
import top.mxzero.security.rbac.service.impl.AuthorizeServiceImpl;
import top.mxzero.security.rbac.service.impl.UserDetailsServiceImpl;

/**
 * @author Peng
 * @since 2024/9/13
 */
@Configuration
@MapperScan("top.mxzero.security.rbac.mapper")
@ComponentScan
public class RbacAutoConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizeService.class)
    public AuthorizeService authorizeService() {
        return new AuthorizeServiceImpl();
    }
}
