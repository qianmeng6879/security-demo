package top.mxzero.security.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import top.mxzero.security.mapper.UserSessionMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@Slf4j
public class LogoutCleanSessionHandler extends SimpleUrlLogoutSuccessHandler implements ApplicationContextAware {
    private ObjectMapper objectMapper;

    private UserSessionMapper sessionMapper;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (new MvcRequestMatcher(null, "/api/**").matches(request)) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(Map.of("message", "退出成功")));
            writer.close();
        } else {
            super.handle(request, response, authentication);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.sessionMapper = applicationContext.getBean(UserSessionMapper.class);
        this.objectMapper = applicationContext.getBean(ObjectMapper.class);
    }
}
