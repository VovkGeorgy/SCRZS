package by.intexsoft.scrzs;

import by.intexsoft.scrzs.config.AppConfig;
import by.intexsoft.scrzs.service.CassandraService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Running");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Finish config context");
        context.getBean(CassandraService.class).getUserData();
//        try {
//            RabbitConnectionService.testConnection();
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//
//        ZKManager zkManager = new ZKManagerImpl();
//        zkManager.getZNodeData("/Node", false);
    }
}
