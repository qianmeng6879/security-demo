package top.mxzero.security.components.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/13
 */
public class JsonAccessDeniedHandler extends AccessDeniedHandlerImpl implements ApplicationContextAware {
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (new MvcRequestMatcher(null, "/api/**").matches(request)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(
                    Map.of("message", "无访问权限"
                    )
            ));
            writer.close();
        } else {
            super.handle(request, response, accessDeniedException);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.objectMapper = applicationContext.getBean(ObjectMapper.class);
    }
}
