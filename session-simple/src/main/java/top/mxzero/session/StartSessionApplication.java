package top.mxzero.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@EnableJdbcHttpSession
@SpringBootApplication
public class StartSessionApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartSessionApplication.class, args);
    }
}
