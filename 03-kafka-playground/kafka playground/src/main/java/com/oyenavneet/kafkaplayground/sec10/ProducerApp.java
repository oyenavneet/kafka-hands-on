package com.oyenavneet.kafkaplayground.sec10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApp {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class,
                "--section=sec10",
                "--config=05-producer"
        );
    }
}
