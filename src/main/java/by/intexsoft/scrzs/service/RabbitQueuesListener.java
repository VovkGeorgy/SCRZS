package by.intexsoft.scrzs.service;

import by.intexsoft.scrzs.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * RabbitMQ queue listeners class
 */
@Slf4j
@Service
@EnableRabbit
public class RabbitQueuesListener {
    private final CassandraUserService cassandraUserService;
    private final ObjectMapper mapper;

    @Autowired
    public RabbitQueuesListener(CassandraUserService cassandraUserService, ObjectMapper mapper) {
        this.cassandraUserService = cassandraUserService;
        this.mapper = mapper;
    }

    /**
     * Read queue listener method
     *
     * @param messageFromQueue message form queue
     */
    @RabbitListener(queues = "ReadQueue")
    public void listenReadQueue(Long messageFromQueue) {
        log.info("Geting message {} from read queue", messageFromQueue);
        cassandraUserService.getUserById(messageFromQueue);
    }

    /**
     * Create queue listener method
     *
     * @param messageFromQueue message form queue
     */
    @RabbitListener(queues = "CreateQueue")
    public void listenCreateQueue(String messageFromQueue) {
        log.info("Geting message from create queue {}", messageFromQueue);
        try {
            cassandraUserService.saveUser(mapper.readValue(messageFromQueue, User.class));
        } catch (IOException e) {
            log.error("Cant parse string message from queue to User class, get exception {}", e.getMessage());
        }
    }

    @RabbitListener(queues = "DeleteQueue")
    public void listenDeleteQueue(Long messageFromQueue) {
        log.info("Geting message {} from delete queue", messageFromQueue);
        cassandraUserService.deleteUserById(messageFromQueue);
    }

}
