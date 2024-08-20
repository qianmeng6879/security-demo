package top.mxzero.security.components.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import top.mxzero.security.entity.UserSession;
import top.mxzero.security.mapper.UserSessionMapper;
import top.mxzero.security.service.MemberService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements ApplicationContextAware {
    private MemberService memberService;
    private ObjectMapper objectMapper;
    private UserSessionMapper sessionMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Date currentTime = new Date();
        String token = null;
        UserSession userSession = new UserSession();
        userSession.setSessionId(request.getSession().getId());
        userSession.setName(authentication.getName());
        userSession.setCreatedAt(currentTime);
        userSession.setLastAccessAt(currentTime);
        userSession.setDeviceType(UserSession.DeviceType.WEB.value());
        if (new MvcRequestMatcher(null, "/api/**").matches(request)) {
            PrintWriter writer = response.getWriter();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            token = UUID.randomUUID().toString().replaceAll("-", "");
            userSession.setDeviceType(UserSession.DeviceType.PHONE.value());
            userSession.setToken(token);
            writer.print(objectMapper.writeValueAsString(
                    Map.of(
                            "session_id", request.getSession().getId(),
                            "token", token
                    )
            ));
            writer.close();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

        memberService.updateLastLogin(authentication.getName());
        sessionMapper.insert(userSession);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.memberService = applicationContext.getBean(MemberService.class);
        this.sessionMapper = applicationContext.getBean(UserSessionMapper.class);
        this.objectMapper = applicationContext.getBean(ObjectMapper.class);
    }
}
