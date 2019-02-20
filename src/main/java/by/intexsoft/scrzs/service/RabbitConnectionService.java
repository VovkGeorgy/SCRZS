package by.intexsoft.scrzs.service;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitConnectionService {

    public static void testConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("192.168.99.100");
        factory.setPort(5673);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare("AMQP default", "direct", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "AMQP default", "black");

        byte[] messageBodyBytes = "New hello world from rabbit".getBytes();
        channel.basicPublish("AMQP default", "black", null, messageBodyBytes);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GetResponse response = channel.basicGet(queueName, false);
        if (response == null) {
            // No message retrieved.
        } else {
            AMQP.BasicProperties props = response.getProps();
            byte[] body = response.getBody();
            System.out.println(new String(body));
            long deliveryTag = response.getEnvelope().getDeliveryTag();
        }
    }
}
