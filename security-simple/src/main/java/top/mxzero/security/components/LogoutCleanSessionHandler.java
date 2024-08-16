package top.mxzero.security.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.StringUtils;

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
public class LogoutCleanSessionHandler extends SimpleUrlLogoutSuccessHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final SessionRegistry sessionRegistry;

    public LogoutCleanSessionHandler(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            // 获取用户的所有会话并清理
            log.info("logout sessionId:{}", request.getRequestedSessionId());
            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(request.getRequestedSessionId());
            if(sessionInformation != null){
                sessionInformation.expireNow();
                sessionRegistry.removeSessionInformation(request.getRequestedSessionId());
            }
        }

        String acceptType = request.getHeader("Accept");
        if (StringUtils.hasLength(acceptType) && acceptType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = response.getWriter();
            writer.print(OBJECT_MAPPER.writeValueAsString(
                    Map.of("message", "退出成功"
                    )
            ));
            writer.close();
        } else {
            super.handle(request, response, authentication);
        }
    }
}
