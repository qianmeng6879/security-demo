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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import top.mxzero.security.entity.UserToken;
import top.mxzero.security.mapper.UserTokenMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/19
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter implements ApplicationContextAware {
    private UserTokenMapper tokenMapper;

    private boolean validateToken(String token, HttpServletRequest request) {
        UserToken userToken = tokenMapper.selectOne(new QueryWrapper<UserToken>().eq("token", token).eq("state", UserToken.TokenState.NORMAL.getState()));
        if (userToken == null) {
            return false;
        }
        Date current = new Date();
        // 判断token上次的访问时间与当前时间差距，使token过期
        if (userToken.getLastAccessAt() == null && (current.getTime() - userToken.getCreatedAt().getTime()) >= userToken.getExpire() * 1000) {
            // token过期
            userToken.setState(UserToken.TokenState.EXPIRE.getState());
            tokenMapper.updateById(userToken);
            return false;
        }

        if (userToken.getLastAccessAt() != null && current.getTime() - userToken.getLastAccessAt().getTime() >= userToken.getExpire() * 1000) {
            userToken.setState(UserToken.TokenState.EXPIRE.getState());
            tokenMapper.updateById(userToken);
            return false;
        }

        userToken.setLastAccessAt(current);
        tokenMapper.updateById(userToken);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userToken.getUsername(), null, Collections.emptyList()));
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (new MvcRequestMatcher(null, "/api/**").matches(request)) {
            String authStr = request.getHeader("Authorization");
            if (authStr != null && authStr.startsWith("Bearer ")) {
                String token = authStr.substring(7);
                validateToken(token, request);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.tokenMapper = applicationContext.getBean(UserTokenMapper.class);
    }
}
