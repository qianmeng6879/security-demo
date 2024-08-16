package top.mxzero.session.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@RestController
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;
    private HttpSession httpSession;
    @RequestMapping("/")
    public Object index(HttpServletRequest request) {
        request.getSession().setAttribute("name", "admin");
        return request.getSession().getId();
    }

    @RequestMapping("/get")
    public Object get(HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        return request.getSession().getAttribute("name");
    }

    @RequestMapping("/list")
    public Object list(HttpServletRequest request) {
        return request.getSession().getAttribute("name");
    }

    @RequestMapping("/expire")
    public Object expire(HttpServletRequest request) {
        request.getSession().invalidate();
        return "";
    }

}
