package com.barbatech.natomada.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AWS configuration properties
 */
@Data
@Component
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String accessKeyId;
    private String secretAccessKey;
    private S3 s3;

    @Data
    public static class S3 {
        private String bucketName;
        private String region;
    }
}
