package top.mxzero.security.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;
import top.mxzero.security.service.MemberService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final MemberService memberService;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public LoginSuccessHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        memberService.updateLastLogin(authentication.getName());
        String acceptType = request.getHeader("Accept");
        if (StringUtils.hasLength(acceptType) && acceptType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {            PrintWriter writer = response.getWriter();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            writer.print(OBJECT_MAPPER.writeValueAsString(
                    Map.of("session_id", request.getSession().getId())
            ));
            writer.close();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
