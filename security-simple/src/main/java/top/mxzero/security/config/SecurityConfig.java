package top.mxzero.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.mxzero.security.components.*;
import top.mxzero.security.service.impl.UserDetailsServiceImpl;

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
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**") // 仅匹配 /api/** 路径
                .authorizeHttpRequests(authorization ->
                        authorization.anyRequest().authenticated() // API 请求必须认证
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态模式
                )
                .exceptionHandling(handler ->
                        handler.accessDeniedHandler(new JsonAccessDeniedHandler())
                                .authenticationEntryPoint(new JSONAuthenticationEntryPoint(LOGIN_PAGE))
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 添加 JWT 过滤器
        return http.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers("/ws/echo").authenticated() // websocket认证
                                .anyRequest().permitAll()
                )
                .formLogin(login ->
                        login.loginPage(LOGIN_PAGE).permitAll()
                                .loginProcessingUrl(LOGIN_PAGE)
                                .successHandler(loginSuccessHandler()) // 登录成功处理器
                                .failureHandler(new LoginFailHandler(LOGIN_PAGE + "?error"))
                )
                .rememberMe(remember ->
                        remember.rememberMeParameter("securityRemember").key("mxzero-top-rem")
                )
                .logout(logout -> {
                    logout.logoutSuccessUrl(LOGIN_PAGE)
                            .logoutSuccessHandler(logoutSuccessHandler());
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutCleanSessionHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/error", "/static/**");
        };
    }
}