package top.mxzero.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Getter
public class UserProfile extends User implements Serializable {
    private final Long id;

    public UserProfile(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}
