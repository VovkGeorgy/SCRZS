package by.intexsoft.scrzs.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Scanning beans in base packages
 */
@Configuration
@Import({SparkCassandraConfig.class})
@ComponentScan(basePackages = "by.intexsoft.scrzs")
public class AppConfig {
}
