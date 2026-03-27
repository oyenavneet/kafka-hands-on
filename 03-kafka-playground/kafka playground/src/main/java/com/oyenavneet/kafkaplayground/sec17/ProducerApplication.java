package com.oyenavneet.kafkaplayground.sec17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.producer")
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                ProducerApplication.class,
                "--section=sec17",
                "--config=02-producer"
        );
    }
}
