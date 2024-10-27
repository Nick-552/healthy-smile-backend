package ru.nick552.healthysmile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nick552.healthysmile.configuration.ApplicationConfig;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class HealthySmileApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthySmileApplication.class, args);
    }
}
