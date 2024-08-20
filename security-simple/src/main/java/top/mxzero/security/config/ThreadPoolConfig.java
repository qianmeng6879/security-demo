package top.mxzero.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@EnableScheduling
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
