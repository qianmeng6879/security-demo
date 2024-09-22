package top.mxzero.endpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.mxzero.security.jwt.components.JSONAuthenticationEntryPoint;
import top.mxzero.security.jwt.components.JSONAccessDeniedHandler;
import top.mxzero.security.jwt.filter.JwtAuthorizationFilter;
import top.mxzero.security.rbac.service.impl.RbacUserDetailsServiceImpl;

/**
 * @author Peng
 * @since 2024/9/17
 */
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
@Import(RbacUserDetailsServiceImpl.class)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JSONAccessDeniedHandler jsonAccessDeniedHandler() {
        return new JSONAccessDeniedHandler();
    }

    @Bean
    public JSONAuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JSONAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/token/**", "/message", "/register", "/public/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(handle -> {
                    handle.accessDeniedHandler(this.jsonAccessDeniedHandler())
                            .authenticationEntryPoint(this.jsonAuthenticationEntryPoint());
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/error", "/static/**", "/favicon.ico");
        };
    }
}
