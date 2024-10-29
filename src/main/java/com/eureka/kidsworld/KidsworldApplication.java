package com.eureka.kidsworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KidsworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(KidsworldApplication.class, args);
    }

}
