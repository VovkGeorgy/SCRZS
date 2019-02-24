package by.intexsoft.scrzs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Application configuration class
 */
@Configuration
@Import({SparkCassandraConfig.class, RabbitConfig.class})
@ComponentScan(basePackages = "by.intexsoft.scrzs")
public class AppConfig {

    /**
     * Been of ObjectMapper wich provides functionality for reading and writing JSON
     *
     * @return objectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
