package top.mxzero.security.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/27
 */
@Configuration
@EnableConfigurationProperties(MinioConfig.MinioProps.class)
public class MinioConfig {
    public static String OSS_RESOURCE_PREFIX = "";

    @Data
    @ConfigurationProperties(prefix = "mxzero.minio")
    public static class MinioProps {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;
    }

    @Bean
    public MinioClient minioClient(MinioProps props) throws MalformedURLException {
        OSS_RESOURCE_PREFIX = props.endpoint + "/" + props.getBucket() + "/";
        return MinioClient.builder()
                .endpoint(new URL(props.getEndpoint()))
                .credentials(props.getAccessKey(), props.getSecretKey())
                .build();
    }
}
