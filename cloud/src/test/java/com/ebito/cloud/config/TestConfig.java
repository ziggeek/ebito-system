package com.ebito.cloud.config;

import com.ebito.cloud.properties.CloudProperties;
import com.ebito.cloud.service.DocumentService;
import com.ebito.cloud.service.Impl.DocumentServiceImpl;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {


    @Bean
    public CloudProperties minioProperties() {
        return new CloudProperties();
    }

    @Bean
    public MinioClient minioClient() {
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    @Primary
    public DocumentService documentService()  {
        return new DocumentServiceImpl(minioClient(), minioProperties());
    }
}
