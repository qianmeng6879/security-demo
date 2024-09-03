package top.mxzero.security.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.mxzero.security.jwt.dto.JwtProps;
import top.mxzero.security.jwt.filter.JwtAuthorizationFilter;
import top.mxzero.security.jwt.service.impl.UserDetailsServiceImpl;

/**
 * @author Peng
 * @since 2024/9/3
 */
@EnableMethodSecurity
@Configuration
@EnableConfigurationProperties(JwtProps.class)
public class JwtSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> {
                    request.requestMatchers("/login").permitAll()
                            .anyRequest().authenticated();
                }).csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/error", "/static/**");
        };
    }

}
