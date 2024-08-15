package top.mxzero.security.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
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
import org.springframework.util.StringUtils;
import top.mxzero.security.service.MemberService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private Cache<Object, Object> cache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        memberService.updateLastLogin(authentication.getName());
        String acceptType = request.getHeader("Accept");
        if (StringUtils.hasLength(acceptType) && acceptType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            PrintWriter writer = response.getWriter();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            cache.put(token, authentication.getName());
            writer.print(OBJECT_MAPPER.writeValueAsString(
                    Map.of(
                            "session_id", request.getSession().getId(),
                            "token", token
                    )
            ));
            writer.close();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cache = applicationContext.getBean(Cache.class);
        this.memberService = applicationContext.getBean(MemberService.class);
    }
}
