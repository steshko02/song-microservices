package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
@SpringBootApplication
@EnableJms
@EnableEurekaClient
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application.yml")
})
@EnableCaching
public class UploadSystemApp {
    public static void main(String[] args) {
        SpringApplication.run(UploadSystemApp.class, args);
    }
}
