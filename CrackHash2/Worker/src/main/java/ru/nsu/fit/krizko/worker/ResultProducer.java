package ru.nsu.fit.krizko.worker;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.krizko.worker.config.RabbitMQConfig;
import ru.nsu.fit.krizko.worker.data.WorkerResult;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ResultProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendResult(UUID requestId, List<String> words) {

        WorkerResult result = new WorkerResult(requestId, words);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESULT_QUEUE,
                result
        );
    }
}
