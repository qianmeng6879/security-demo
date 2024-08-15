package top.mxzero.oauth.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/5
 */
@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public LoginFailHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String acceptType = request.getHeader("Accept");
        if (StringUtils.hasLength(acceptType) && acceptType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = response.getWriter();
            writer.print(OBJECT_MAPPER.writeValueAsString(
                    Map.of("message", exception.getMessage()
                    )
            ));
            writer.close();
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
