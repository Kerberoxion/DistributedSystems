package ru.nsu.fit.krizko.manager;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.krizko.manager.config.RabbitMQConfig;
import ru.nsu.fit.krizko.manager.data.WorkerResult;
import ru.nsu.fit.krizko.manager.service.ManagerService;

@Component
@AllArgsConstructor
public class ResultConsumer {
    private final ManagerService managerService;

    @RabbitListener(queues = RabbitMQConfig.RESULT_QUEUE)
    public void receiveResult(WorkerResult result) {
        managerService.acceptResult(result);
    }
}

