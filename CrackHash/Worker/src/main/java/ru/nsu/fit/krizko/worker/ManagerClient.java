package ru.nsu.fit.krizko.worker;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.nsu.fit.krizko.worker.data.WorkerResult;

import java.util.List;
import java.util.UUID;

@Component
public class ManagerClient {

    private final RestClient restClient;

    public ManagerClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void sendResult(UUID requestId, List<String> foundWords){
    restClient.post()
            .uri("/api/hash/result")
        .body(new WorkerResult(requestId, foundWords))
            .retrieve()
        .toBodilessEntity();
    }
}
