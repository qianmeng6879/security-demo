package top.mxzero.endpoint;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Peng
 * @since 2024/9/17
 */
@MapperScan("top.mxzero.endpoint.mapper")
@SpringBootApplication
public class StartEndpointApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartEndpointApplication.class, args);
    }
}
