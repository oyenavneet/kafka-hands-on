package com.oyenavneet.kafkaplayground.sec20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,
                "--section=sec20",
                "--config=04-sasl-ssl-producer"
        );
    }
}
