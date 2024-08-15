package top.mxzero.security.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
public class JSONSessionExpiredStrategy implements SessionInformationExpiredStrategy {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();

        String acceptType = request.getHeader("Accept");
        if (StringUtils.hasLength(acceptType) && acceptType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = response.getWriter();
            writer.print(OBJECT_MAPPER.writeValueAsString(
                    Map.of("message", "会话过期，请重新登录"
                    )
            ));
            writer.close();
        } else {
            response.getWriter().print("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");
            response.flushBuffer();
        }
    }
}
