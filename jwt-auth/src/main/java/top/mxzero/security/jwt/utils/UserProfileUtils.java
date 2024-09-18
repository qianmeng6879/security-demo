package top.mxzero.security.jwt.utils;

import top.mxzero.common.dto.UserProfile;

/**
 * @author Peng
 * @since 2024/9/18
 */
public final class UserProfileUtils {
    private static final ThreadLocal<UserProfile> THREAD_LOCAL = new ThreadLocal<>();

    private UserProfileUtils() {
    }

    public static UserProfile get() {
        UserProfile userProfile = THREAD_LOCAL.get();
        if (userProfile == null) {
            throw new RuntimeException("UserProfile is null");
        }
        return userProfile;
    }

    public static Long getId() {
        UserProfile userProfile = THREAD_LOCAL.get();
        if (userProfile == null) {
            throw new RuntimeException("UserProfile is null");
        }
        return userProfile.getId();
    }

    public static void set(UserProfile userProfile) {
        if (userProfile == null) {
            throw new RuntimeException("UserProfile is null");
        }
        THREAD_LOCAL.set(userProfile);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
