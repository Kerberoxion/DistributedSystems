package ru.nsu.fit.krizko.manager;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.krizko.manager.config.RabbitMQConfig;
import ru.nsu.fit.krizko.manager.data.WorkerRequest;

@Component
@AllArgsConstructor
public class TaskProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendTask(WorkerRequest request) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_QUEUE, request);
    }
}
