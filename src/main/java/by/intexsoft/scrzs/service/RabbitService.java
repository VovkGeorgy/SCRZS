package by.intexsoft.scrzs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * RabbiMQ messages service
 */
@Slf4j
@Service
public class RabbitService {

    @Value("${rabbitmq.messagesExchange}")
    private String exchange;

    @Value("${rabbitmq.messagesReadRoutingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Method send message to read queue
     *
     * @param message message
     */
    public void sendToReadQueue(Long message) {
        log.debug("Sending message {} to read queue", message);
        rabbitTemplate.convertAndSend(exchange, "read", message);
    }

    /**
     * Method send message to create queue
     *
     * @param message message
     */
    public void sendToCreateQueue(String message) {
        log.debug("Sending message {} to create queue", message);
        rabbitTemplate.convertAndSend(exchange, "create", message);
    }
}
