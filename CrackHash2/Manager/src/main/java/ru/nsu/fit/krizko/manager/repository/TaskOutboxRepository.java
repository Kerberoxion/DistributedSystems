package ru.nsu.fit.krizko.manager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nsu.fit.krizko.manager.model.OutboxStatus;
import ru.nsu.fit.krizko.manager.model.TaskOutbox;

import java.util.List;

public interface TaskOutboxRepository extends MongoRepository<TaskOutbox, String> {
    List<TaskOutbox> findByStatus(OutboxStatus status);
}
