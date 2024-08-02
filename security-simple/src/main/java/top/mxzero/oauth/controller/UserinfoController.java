package top.mxzero.oauth.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/1
 */
@RestController
public class UserinfoController {
    @RequestMapping("/userinfo")
    public Object userinfoApi(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken user) {
            String nickname = user.getPrincipal().getAttribute("name");
            String avatarUrl = user.getPrincipal().getAttribute("avatar_url");
            return Map.of(
                    "nickname", nickname,
                    "avatar", avatarUrl,
                    "id",principal.getName()
            );
        }
        return principal.getName();
    }
}
