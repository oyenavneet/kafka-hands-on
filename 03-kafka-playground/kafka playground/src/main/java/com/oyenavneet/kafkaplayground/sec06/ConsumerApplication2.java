package com.oyenavneet.kafkaplayground.sec06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class ConsumerApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication2.class,
                "--section=sec06",
                "--config=01-consumer"
        );
    }
}
