package com.resteurant.bi.poc.skytabbi.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchJestConfig {
    @Value("http://${spring.elasticsearch.uris}")
    private String elasticsearchHost;

    @Bean
    public JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(elasticsearchHost)
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }
}
