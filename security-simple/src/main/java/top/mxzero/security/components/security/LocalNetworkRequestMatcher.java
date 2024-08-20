package top.mxzero.security.components.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Pattern;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
public class LocalNetworkRequestMatcher implements RequestMatcher {

    private static final Pattern LOCAL_NETWORK_PATTERN = Pattern.compile(
            "^(192\\.168\\.|10\\.|172\\.(1[6-9]|2[0-9]|3[0-1])\\.).*"
    );

    private final String[] pathList;

    public LocalNetworkRequestMatcher(String... paths) {
        this.pathList = paths;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        String requestPath = request.getRequestURI();

        return isLocalNetwork(clientIp) && matchesPath(requestPath);
    }

    private boolean isLocalNetwork(String clientIp) {
        return LOCAL_NETWORK_PATTERN.matcher(clientIp).matches();
    }

    private boolean matchesPath(String requestPath) {
        for (String path : pathList) {
            if (requestPath.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        // 处理可能的多个 IP 地址，取第一个（即最原始的客户端 IP）
        return xfHeader.split(",")[0].trim();
    }
}