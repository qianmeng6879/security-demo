package top.mxzero.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
@Slf4j
//@RestController
//@RequestMapping("/session")
public class LoginUserController {
    @Autowired
    public SessionRegistry sessionRegistry;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users/break/{sessionId}")
    public String breakUserApi(@PathVariable("sessionId") String sessionId) {
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (sessionInformation != null) {
            sessionInformation.expireNow();
            sessionRegistry.removeSessionInformation(sessionId);
            log.info("break session:{}", sessionInformation.getSessionId());
        }

        return "success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users")
    public Object userNameListApi() {
        Map<String, Object> userData = new HashMap<>();

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            List<Object> list = new ArrayList<>();

            UserDetails userDetails = (UserDetails) principal;

            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInformation : sessions) {
                Map<String, Object> data = new HashMap<>();
                data.put("session_id", sessionInformation.getSessionId());
                data.put("principal", principal);
                data.put("isExpire", sessionInformation.isExpired());
                list.add(data);
            }

            userData.put(userDetails.getUsername(), list);

        }
        return userData;
    }
}
