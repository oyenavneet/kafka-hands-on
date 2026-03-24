package com.oyenavneet.kafkaplayground.sec12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,
                "--section=sec12",
                "--config=02-producer"
        );
    }
}
