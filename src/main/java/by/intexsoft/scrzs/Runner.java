package by.intexsoft.scrzs;

import by.intexsoft.scrzs.config.AppConfig;
import by.intexsoft.scrzs.repository.CassandraRepository;
import by.intexsoft.scrzs.service.RabbitConnectionService;
import by.intexsoft.scrzs.service.ZKManager;
import by.intexsoft.scrzs.service.ZKManagerImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Long count = context.getBean(CassandraRepository.class).getUserRowsCount();
        System.out.println(count);
        try {
            RabbitConnectionService.testConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("Running");

        ZKManager zkManager = new ZKManagerImpl();
        zkManager.getZNodeData("/Node", false);
    }
}
