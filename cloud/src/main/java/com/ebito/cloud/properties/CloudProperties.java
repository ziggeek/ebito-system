package com.ebito.cloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class CloudProperties {
    private String bucket;
    private String url;
    private String accessKey;
    private String secretKey;

}
