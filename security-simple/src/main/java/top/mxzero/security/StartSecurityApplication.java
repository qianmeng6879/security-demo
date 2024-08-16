package top.mxzero.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@EnableJdbcHttpSession
@SpringBootApplication
public class StartSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartSecurityApplication .class,args);
    }
}
