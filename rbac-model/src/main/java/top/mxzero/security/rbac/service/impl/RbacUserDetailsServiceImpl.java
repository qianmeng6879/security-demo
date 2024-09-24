package top.mxzero.security.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.mxzero.common.dto.UserProfile;
import top.mxzero.security.rbac.entity.User;
import top.mxzero.security.rbac.mapper.UserMapper;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Peng
 * @since 2024/9/13
 */
public class RbacUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorizeService authorizeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authorizeService.getUserProfile(username);
    }
}
