package ru.nsu.fit.krizko.manager.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.krizko.manager.WorkerClient;
import ru.nsu.fit.krizko.manager.data.WorkerRequest;
import ru.nsu.fit.krizko.manager.data.WorkerResult;
import ru.nsu.fit.krizko.manager.model.CrackRequestState;
import ru.nsu.fit.krizko.manager.model.RequestStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ManagerService {

    private final WorkerClient workerClient;
    private final Map<UUID, CrackRequestState> storage = new ConcurrentHashMap<>();
    private static final int WORKER_COUNT = 3;

    public ManagerService(WorkerClient workerClient) {
        this.workerClient = workerClient;
    }

    public UUID createRequest(String hash, int maxLength) {

        UUID id = UUID.randomUUID();

        storage.put(id, new CrackRequestState(
                RequestStatus.IN_PROGRESS,
                WORKER_COUNT
        ));

        List<Character> list = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) list.add(c);
        for (char c = '0'; c <= '9'; c++) list.add(c);

        new Thread(() -> {
            for (int partNumber = 0; partNumber < WORKER_COUNT; partNumber++) {

                WorkerRequest workerRequest = new WorkerRequest(id, hash, maxLength, list, partNumber, WORKER_COUNT);
                workerClient.crackHash(workerRequest);
            }

        }).start();

        return id;
    }

    public CrackRequestState getStatus(UUID id) {
        return storage.get(id);
    }

    public void acceptResult(WorkerResult result) {

        CrackRequestState state = storage.get(result.getRequestId());

        if (state != null) {
            state.getData().addAll(result.getWords());
            state.incrementCompletedParts();

            if (state.getCompletedParts() == state.getTotalParts()) {
                state.setStatus(RequestStatus.READY);
            }
        }
    }

    public String ping(int workerIndex){
       return workerClient.ping(workerIndex);
    }
}
