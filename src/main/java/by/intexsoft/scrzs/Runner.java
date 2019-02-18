package by.intexsoft.scrzs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Runner {

    public static void main(String[] args) throws IOException, TimeoutException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, RabbitConfig.class);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5673);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare("AMQP default", "direct", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "AMQP default", "black");

        byte[] messageBodyBytes = "New hello world".getBytes();
        channel.basicPublish("AMQP default", "black", null, messageBodyBytes);

        System.out.println("Running");
    }
}
