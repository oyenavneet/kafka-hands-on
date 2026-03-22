package com.oyenavneet.kafkaplayground.sec10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class USPSConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(USPSConsumerApp.class,
                "--section=sec10",
                "--config=03-usps-consumer"
        );
    }
}
