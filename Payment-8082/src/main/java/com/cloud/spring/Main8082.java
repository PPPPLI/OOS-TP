package com.cloud.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.cloud.spring.repository"})
public class Main8082 {
    public static void main(String[] args) {
        SpringApplication.run(Main8082.class,args);
    }
}