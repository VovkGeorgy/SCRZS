package by.intexsoft.scrzs.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Scanning beans in base packages
 */
@Configuration
@ComponentScan(basePackages = "by.intexsoft.scrzs")
public class AppConfig {
}