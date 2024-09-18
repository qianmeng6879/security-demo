package top.mxzero.common.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Getter
public class UserProfile extends User {
    private final Long id;

    public UserProfile(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}
