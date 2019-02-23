package by.intexsoft.scrzs;

import by.intexsoft.scrzs.config.AppConfig;
import by.intexsoft.scrzs.service.RabbitConnectionService;
import by.intexsoft.scrzs.service.ZKManager;
import by.intexsoft.scrzs.service.impl.ZKManagerImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Running");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Finish config context");
//        context.getBean(CassandraService.class).getUserData();

        try {
            RabbitConnectionService.testConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        ZKManager zkManager = context.getBean(ZKManagerImpl.class);
        zkManager.getZNodeData("/Node", false);
    }
}
