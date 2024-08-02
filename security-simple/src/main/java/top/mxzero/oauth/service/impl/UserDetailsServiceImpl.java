package top.mxzero.oauth.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.mxzero.oauth.entity.Member;
import top.mxzero.oauth.service.MemberService;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
public class UserDetailsServiceImpl implements UserDetailsService, ApplicationContextAware {
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException(String.format("用户名 %s 不存在", username));
        }

        List<SimpleGrantedAuthority> authorityList = memberService.getRolesByMemberId(member.getId())
                .stream().map(SimpleGrantedAuthority::new).toList();

        return new User(username, member.getPassword(), authorityList);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.memberService = applicationContext.getBean(MemberService.class);
    }
}
