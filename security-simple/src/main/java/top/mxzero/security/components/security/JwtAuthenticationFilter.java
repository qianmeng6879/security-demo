package top.mxzero.security.components.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import top.mxzero.security.entity.UserSession;
import top.mxzero.security.mapper.UserSessionMapper;

import java.io.IOException;
import java.util.Collections;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter implements ApplicationContextAware {

    private UserSessionMapper sessionMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (new MvcRequestMatcher(null, "/api/**").matches(request)) {
            String token = request.getHeader("Authorization");
            Authentication authentication = null;
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                authentication = getAuthentication(token);
            } else if (request.getParameter("token") != null) {
                authentication = getAuthentication(request.getParameter("token"));
            }

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        UserSession userSession = sessionMapper.selectOne(new QueryWrapper<UserSession>().eq("token", token));
        if (userSession != null) {
            return new UsernamePasswordAuthenticationToken(userSession.getName(), null, Collections.emptyList());
        }
        return null; // 示例，实际应返回有效的 Authentication 对象
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.sessionMapper = applicationContext.getBean(UserSessionMapper.class);
    }
}
