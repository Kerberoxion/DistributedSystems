package ru.nsu.fit.krizko.worker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ManagerClientConfig {

    @Bean
    public RestClient managerRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://manager:8080")
                .build();
    }
}