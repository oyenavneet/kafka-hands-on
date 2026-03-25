package com.oyenavneet.kafkaplayground.sec13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class DigitalDeliveryConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(DigitalDeliveryConsumerApp.class,
                "--section=sec13",
                "--config=01-digital-consumer"
        );
    }
}
