package ru.nsu.fit.krizko.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.krizko.manager.data.WorkerRequest;
import ru.nsu.fit.krizko.manager.data.WorkerResult;
import ru.nsu.fit.krizko.manager.model.CrackRequestState;
import ru.nsu.fit.krizko.manager.model.OutboxStatus;
import ru.nsu.fit.krizko.manager.model.RequestStatus;
import ru.nsu.fit.krizko.manager.model.TaskOutbox;
import ru.nsu.fit.krizko.manager.repository.RequestRepository;
import ru.nsu.fit.krizko.manager.repository.TaskOutboxRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ManagerService {

    private final TaskOutboxRepository outboxRepository;
    private final RequestRepository taskRepository;
    private static final int WORKER_COUNT = 3;

    public UUID createRequest(String hash, int maxLength) {

        UUID id = UUID.randomUUID();

        List<Character> list = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) list.add(c);
        for (char c = '0'; c <= '9'; c++) list.add(c);

        taskRepository.save(new CrackRequestState(id.toString(), hash, maxLength, WORKER_COUNT));

        for (int partNumber = 0; partNumber < WORKER_COUNT; partNumber++) {
            WorkerRequest workerRequest = new WorkerRequest(id, hash, maxLength, list, partNumber, WORKER_COUNT);

            outboxRepository.save(
                    new TaskOutbox(
                            null,
                            workerRequest,
                            OutboxStatus.PENDING,
                            System.currentTimeMillis()
                    )
            );
        }

        return id;
    }

    public CrackRequestState getStatus(UUID id) {
        return taskRepository.findByRequestId(id.toString()).orElse(new CrackRequestState());
    }

    public void acceptResult(WorkerResult result) {

        CrackRequestState state = taskRepository.findByRequestId(result.getRequestId().toString())
                .orElseThrow(() -> new RuntimeException("Request not found: " + result.getRequestId().toString()));

        state.getData().addAll(result.getWords());
        state.incrementCompletedParts();

        if (state.getCompletedParts() == state.getTotalParts()) {
            state.setStatus(RequestStatus.READY);
        }

        taskRepository.save(state);

    }
}
