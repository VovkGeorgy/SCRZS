package by.intexsoft.scrzs;

import by.intexsoft.scrzs.config.AppConfig;
import by.intexsoft.scrzs.service.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Class for application running
 */
@Slf4j
public class Runner {

    /**
     * Application entry point
     *
     * @param args cmd input arguments
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        RabbitService rabbit = context.getBean(RabbitService.class);
        log.info("Testing application work!");
        rabbit.sendToDeleteQueue(99050L);
//        rabbit.sendToReadQueue(88055555L);
//        rabbit.sendToCreateQueue("{ \n" +
//                "\t\"userid\": 12312321,\n" +
//                "    \"username\": \"userFromJsonMessage\",\n" +
//                "\t\"firstname\": \"James\",\n" +
//                "\t\"lastname\": \"Oflin\",\n" +
//                "\t\"age\": 72,\n" +
//                "\t\"phonenumber\": \"+4819213213\"\n" +
//                "}");
    }
}
