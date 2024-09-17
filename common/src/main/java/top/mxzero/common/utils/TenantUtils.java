package top.mxzero.common.utils;

/**
 * @author Peng
 * @since 2024/9/18
 */
public class TenantUtils {
    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();


    public static Long getTenantId() {
        return THREAD_LOCAL.get();
    }

    public static void setTenantId(Long tenantId) {
        THREAD_LOCAL.set(tenantId);
    }

    public static void clean() {
        THREAD_LOCAL.remove();
    }
}
