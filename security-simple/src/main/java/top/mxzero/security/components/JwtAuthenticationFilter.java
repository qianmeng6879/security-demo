package top.mxzero.security.components;

import com.github.benmanes.caffeine.cache.Cache;
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

import java.io.IOException;
import java.util.Collections;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter implements ApplicationContextAware {
    private Cache<Object, Object> cache;

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
        Object name = cache.getIfPresent(token);
        if (name != null) {
            return new UsernamePasswordAuthenticationToken(name.toString(), null, Collections.emptyList());
        }
        return null; // 示例，实际应返回有效的 Authentication 对象
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cache = applicationContext.getBean(Cache.class);
    }
}
