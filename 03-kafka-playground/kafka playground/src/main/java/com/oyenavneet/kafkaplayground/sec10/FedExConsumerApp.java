package com.oyenavneet.kafkaplayground.sec10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class FedExConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(FedExConsumerApp.class,
                "--section=sec10",
                "--config=02-fedex-consumer"
        );
    }
}
