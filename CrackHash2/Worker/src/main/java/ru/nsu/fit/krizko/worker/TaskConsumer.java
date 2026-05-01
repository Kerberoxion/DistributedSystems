package ru.nsu.fit.krizko.worker;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.nsu.fit.krizko.worker.config.RabbitMQConfig;
import ru.nsu.fit.krizko.worker.data.CrackHashTask;
import ru.nsu.fit.krizko.worker.service.WorkerService;

@Component
@AllArgsConstructor
public class TaskConsumer {
    private final WorkerService workerService;
    static final Logger log =
            LoggerFactory.getLogger(TaskConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.TASK_QUEUE)
    public void receive(CrackHashTask request,
                        Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag
                        ) {
        try {
            workerService.crackHash(
                    request.getRequestId(),
                    request.getAlphabet(),
                    request.getHash(),
                    request.getMaxLength(),
                    request.getPartNumber(),
                    request.getPartCount()
            );

            channel.basicAck(tag, false);

        } catch (Exception e) {

            try {
                channel.basicNack(tag, false, true);
            } catch (Exception ex) {
                log.error("Error occurred while nacking", ex);
            }
        }
    }
}
