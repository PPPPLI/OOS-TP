package com.cloud.spring.tp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.cloud.spring.tp1.jpaRepository"})
public class Main8081 {

    public static void main(String[] args) {
        SpringApplication.run(Main8081.class, args);
    }

}
