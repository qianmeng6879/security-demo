package top.mxzero.security.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author Peng
 * @since 2024/9/3
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return new User(username, passwordEncoder.encode("hello"), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
        throw new UsernameNotFoundException("用户【" + username + "】不存在");
    }
}
