package com.oyenavneet.kafkaplayground.sec14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class PhysicalDeliveryConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(PhysicalDeliveryConsumerApp.class,
                "--section=sec14",
                "--config=02-physical-consumer"
        );
    }
}
