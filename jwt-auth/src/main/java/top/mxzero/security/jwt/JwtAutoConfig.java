package top.mxzero.security.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.mxzero.security.jwt.dto.JwtProps;
import top.mxzero.security.jwt.filter.JwtAuthorizationFilter;

/**
 * @author Peng
 * @since 2024/9/3
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(JwtProps.class)
public class JwtAutoConfig {

    @Bean
    @ConditionalOnMissingBean(JwtAuthorizationFilter.class)
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }
}
