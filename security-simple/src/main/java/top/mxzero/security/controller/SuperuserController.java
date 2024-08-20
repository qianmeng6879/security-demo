package top.mxzero.security.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.entity.Member;
import top.mxzero.security.mapper.MemberMapper;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@RestController
public class SuperuserController {
    @Autowired
    private MemberMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/superuser/create")
    public String createSuperUser(@RequestBody @Valid ReqBody data) {
        Member member = new Member();
        member.setUsername(data.getUsername());
        member.setNickname(data.getUsername());
        member.setEmail(data.getEmail());
        member.setPassword(passwordEncoder.encode(data.getPassword()));
        member.setIsSuperuser(1);
        mapper.insert(member);
        return "success";
    }

    @Data
    public static class ReqBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @Email
        private String email;
    }
}
