package top.mxzero.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import top.mxzero.common.utils.TenantUtils;

/**
 * @author Peng
 * @since 2024/9/18
 */
public class TenantHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = request.getHeader("X-Tenant-ID");
        if (!StringUtils.hasLength(tenantId)) {
            tenantId = request.getParameter("tenant_id");
        }
        if (StringUtils.hasLength(tenantId)) {
            TenantUtils.setTenantId(Long.parseLong(tenantId));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantUtils.clean();
    }
}
