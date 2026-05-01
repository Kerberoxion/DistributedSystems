package ru.nsu.fit.krizko.worker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {
    public static final String TASK_QUEUE = "task_queue";
    public static final String RESULT_QUEUE = "result_queue";


    @Bean
    public Queue myQueue(){return new Queue(TASK_QUEUE, true);}

    @Bean
    public Queue resultQueue() {
        return new Queue(RESULT_QUEUE);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
