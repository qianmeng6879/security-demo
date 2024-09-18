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
        User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户【" + username + "】不存在");
        }

        List<SimpleGrantedAuthority> authorities = this.authorizeService.roleNameByUserId(user.getId()).stream().map(SimpleGrantedAuthority::new).toList();

        return new UserProfile(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }
}
