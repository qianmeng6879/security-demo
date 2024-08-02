package top.mxzero.oauth.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.mxzero.oauth.entity.Member;
import top.mxzero.oauth.exceptions.ServiceException;
import top.mxzero.oauth.service.MemberService;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Slf4j
@Controller
public class RegisterController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAction(Member member, String code, HttpSession session) {
        if (!StringUtils.hasLength(member.getUsername())
                || !StringUtils.hasLength(member.getPassword())
                || !StringUtils.hasLength(code)
        ) {
            session.setAttribute("error", "用户名、密码、验证码不能为空");
            return "redirect:/register?error";
        }

        Object imageCode = session.getAttribute("imageCode");
        if (imageCode == null || !code.equalsIgnoreCase(imageCode.toString())) {
            session.setAttribute("error", "验证码错误");
            return "redirect:/register?error";
        }


        try {
            memberService.save(member);
            return "redirect:/login";
        } catch (ServiceException e) {
            session.setAttribute("error", e.getMessage());
            return "redirect:/register?error";
        } catch (Exception e) {
            log.error(e.getMessage());
            session.setAttribute("error", "注册失败，请稍后再试！");
            return "redirect:/register?error";
        }
    }
}
