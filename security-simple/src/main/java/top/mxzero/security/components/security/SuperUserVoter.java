package top.mxzero.security.components.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */

public class SuperUserVoter implements AccessDecisionVoter<Object> {

    public static final String SUPER_USER_ROLE = "ROLE_SUPERUSER";

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (SUPER_USER_ROLE.equals(authority.getAuthority())) {
                // 如果用户是超级用户，直接允许访问
                return ACCESS_GRANTED;
            }
        }

        // 继续其他权限的校验
        return ACCESS_ABSTAIN;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
