package top.mxzero.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.mxzero.common.handler.TenantHandlerInterceptor;

/**
 * @author Peng
 * @since 2024/9/18
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantHandlerInterceptor()).addPathPatterns("/**");
    }
}
