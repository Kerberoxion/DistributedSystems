package ru.nsu.fit.krizko.manager;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.nsu.fit.krizko.manager.data.WorkerRequest;

import java.util.List;

@Component
public class WorkerClient {

    private final List<RestClient> workers;

    public WorkerClient(List<RestClient> workers) {
        this.workers = workers;
    }

    public String ping(int workerIndex) {
        return workers.get(workerIndex)
                .get()
                .uri("/internal/api/worker/hash/crack/ping")
                .retrieve()
                .body(String.class);
    }

    public void crackHash(WorkerRequest workerRequest){
        workers.get(workerRequest.getPartNumber())
                .post()
                .uri("/internal/api/worker/hash/crack/task")
                .body(workerRequest)
                .retrieve()
                .toBodilessEntity();
    }
}
