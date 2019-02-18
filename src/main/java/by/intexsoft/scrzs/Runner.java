package by.intexsoft.scrzs;

import by.intexsoft.scrzs.service.RabbitConnectionService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Runner {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, RabbitConfig.class);
        try {
            RabbitConnectionService.testConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("Running");

//        ZKManager zkManager = new ZKManagerImpl();
//        zkManager.getZNodeData("/Node", false);
    }
}
