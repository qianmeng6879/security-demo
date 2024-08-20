package top.mxzero.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@EnableAsync
@EnableRedisHttpSession
@SpringBootApplication
public class StartSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartSecurityApplication.class, args);
    }
}
