package com.barbatech.natomada.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate bean with timeout settings
 * to enable fallback when external APIs are slow or unavailable
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10 seconds to establish connection
        factory.setReadTimeout(10000);    // 10 seconds to read response
        return new RestTemplate(factory);
    }
}
