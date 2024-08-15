package top.mxzero.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import top.mxzero.oauth.components.security.*;
import top.mxzero.oauth.service.MemberService;
import top.mxzero.oauth.service.impl.UserDetailsServiceImpl;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    public static final String LOGIN_PAGE = "/login";

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            MemberService memberService
    ) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll())
                .rememberMe(config -> config.key("securityRemember"))
                .logout(LogoutConfigurer::permitAll)
                .formLogin(login -> login.loginPage(LOGIN_PAGE).permitAll().loginProcessingUrl(LOGIN_PAGE)
                        .successHandler(new LoginSuccessHandler(memberService))
                        .failureHandler(new LoginFailHandler(LOGIN_PAGE + "?error"))
                )
                .exceptionHandling(handler -> {
                    handler
                            .accessDeniedHandler(new JsonAccessDeniedHandler())
                            .authenticationEntryPoint(new JSONAuthenticationEntryPoint(LOGIN_PAGE));
                })
                .sessionManagement(session ->
                        session.maximumSessions(1)
                                .expiredSessionStrategy(new JSONSessionExpiredStrategy())
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/error", "/static/**");
        };
    }
}