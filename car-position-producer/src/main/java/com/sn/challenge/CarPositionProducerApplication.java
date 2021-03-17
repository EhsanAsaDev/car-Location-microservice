package com.sn.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarPositionProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarPositionProducerApplication.class, args);
    }

}
