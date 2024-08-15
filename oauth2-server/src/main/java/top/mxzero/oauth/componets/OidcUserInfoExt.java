package top.mxzero.oauth.componets;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/15
 */
public class OidcUserInfoExt implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {
    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext context) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", context.getAuthentication().getName());
        claims.put("state", "normal");
        return new OidcUserInfo(claims);
    }
}
