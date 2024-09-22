package top.mxzero.common.components;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Peng
 * @since 2024/9/21
 */
@Slf4j
public class RedisIdentifierGenerator implements IdentifierGenerator, ApplicationContextAware {
    private static final String ID_KEY_SUFFIX = ".id";
    private StringRedisTemplate redisTemplate;

    @Override
    public Number nextId(Object entity) {
        return this.redisTemplate.opsForValue().increment(entity.getClass().getName() + ID_KEY_SUFFIX);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }
}
