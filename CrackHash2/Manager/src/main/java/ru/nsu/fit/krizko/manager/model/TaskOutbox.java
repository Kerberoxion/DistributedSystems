package ru.nsu.fit.krizko.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.nsu.fit.krizko.manager.data.WorkerRequest;

@Document(collection = "task_outbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskOutbox {

    @Id
    private String id;

    private WorkerRequest task;

    private OutboxStatus status;

    private long createdAt;
}