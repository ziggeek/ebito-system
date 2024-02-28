package com.ebito.cloud.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ebito.cloud.properties.CloudProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class S3Config {
    private final CloudProperties cloudProperties;

    @Bean
    public AmazonS3 amazonS3() {

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(cloudProperties.getAccessKey(), cloudProperties.getSecretKey());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(
                        "storage.yandexcloud.net", "ru-central1"))
                .build();
    }
}
