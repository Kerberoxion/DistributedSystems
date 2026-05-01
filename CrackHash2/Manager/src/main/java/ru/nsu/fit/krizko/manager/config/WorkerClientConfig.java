package ru.nsu.fit.krizko.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WorkerClientConfig {

    @Bean
    public List<RestClient> workerClients(
            RestClient.Builder builder,
            WorkerProperties properties
    ) {
        return properties.getUrls().stream()
                .map(url -> builder.baseUrl(url).build())
                .toList();
    }
}