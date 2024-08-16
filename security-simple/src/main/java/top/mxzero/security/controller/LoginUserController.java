package top.mxzero.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.entity.UserSession;
import top.mxzero.security.mapper.UserSessionMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
@Slf4j
@RestController
@RequestMapping("/session")
public class LoginUserController {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserSessionMapper sessionMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("expire/{sessionId}")
    public String breakUserApi(@PathVariable("sessionId") String sessionId) {
        sessionRepository.deleteById(sessionId);
//        sessionMapper.deleteById(sessionId);
        return "success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users")
    public Object userNameListApi() {
        Map<String, List<UserSession>> collect = sessionMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(UserSession::getName));
        return collect;
    }
}
