package top.mxzero.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 * @since 2024/9/19
 */
public class RedisUtils implements ApplicationContextAware {
    private static StringRedisTemplate redisTemplate;

    private RedisUtils() {
    }

    public static void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public static String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }


}
