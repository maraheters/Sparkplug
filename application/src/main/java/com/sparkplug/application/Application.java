package com.sparkplug.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.sparkplug")
@EntityScan(basePackages = {
        "com.sparkplug.catalog",
        "com.sparkplug.auth"
})
@EnableJpaRepositories(basePackages = {
        "com.sparkplug.catalog",
        "com.sparkplug.auth"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
