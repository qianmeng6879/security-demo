package top.mxzero.security.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.mxzero.security.rbac.service.AuthorizeService;
import top.mxzero.security.rbac.service.impl.AuthorizeServiceImpl;
import top.mxzero.security.rbac.service.impl.RbacUserDetailsServiceImpl;

/**
 * @author Peng
 * @since 2024/9/13
 */
@Configuration
@MapperScan("top.mxzero.security.rbac.mapper")
@ComponentScan
public class RbacAutoConfig {
    @Bean
    public AuthorizeService authorizeService(){
        return new AuthorizeServiceImpl();
    }
}
