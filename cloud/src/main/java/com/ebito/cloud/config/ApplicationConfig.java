package com.ebito.cloud.config;

import com.ebito.cloud.properties.CloudProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfig {
    private final CloudProperties cloudProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(cloudProperties.getUrl())
                .credentials(cloudProperties.getAccessKey(),
                        cloudProperties.getSecretKey())
                .build();
    }
}
