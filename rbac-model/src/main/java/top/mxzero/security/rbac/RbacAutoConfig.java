package top.mxzero.security.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.mxzero.security.rbac.service.AuthorizeService;
import top.mxzero.security.rbac.service.impl.AuthorizeServiceImpl;

/**
 * @author Peng
 * @since 2024/9/13
 */
@Configuration
@MapperScan("top.mxzero.security.rbac.mapper")
@ComponentScan
public class RbacAutoConfig {
    @Bean
    public AuthorizeService authorizeService() {
        return new AuthorizeServiceImpl();
    }
}
