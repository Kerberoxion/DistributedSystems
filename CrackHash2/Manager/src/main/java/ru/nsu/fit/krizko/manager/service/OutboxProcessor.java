package ru.nsu.fit.krizko.manager.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nsu.fit.krizko.manager.TaskProducer;
import ru.nsu.fit.krizko.manager.model.OutboxStatus;
import ru.nsu.fit.krizko.manager.model.TaskOutbox;
import ru.nsu.fit.krizko.manager.repository.TaskOutboxRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OutboxProcessor {

    private final TaskOutboxRepository outboxRepository;
    private final TaskProducer taskProducer;

    static final Logger log =
            LoggerFactory.getLogger(OutboxProcessor.class);

    @Scheduled(fixedDelay = 3000)
    public void processOutbox() {

        List<TaskOutbox> tasks = outboxRepository.findByStatus(OutboxStatus.PENDING);

        for (TaskOutbox task : tasks) {
            try {
                taskProducer.sendTask(task.getTask());

                task.setStatus(OutboxStatus.SENT);
                outboxRepository.save(task);

            } catch (Exception e) {
                // RabbitMQ недоступен → просто оставляем PENDING
                log.error("RabbitMQ недоступен{}", e.getMessage());
            }
        }
    }
}