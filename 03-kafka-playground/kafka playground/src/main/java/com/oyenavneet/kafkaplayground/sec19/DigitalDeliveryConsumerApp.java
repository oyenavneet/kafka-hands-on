package com.oyenavneet.kafkaplayground.sec19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class DigitalDeliveryConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(DigitalDeliveryConsumerApp.class,
                "--section=sec19",
                "--config=01-digital-consumer"
        );
    }
}
