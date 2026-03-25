package com.oyenavneet.kafkaplayground.sec13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApp {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class,
                "--section=sec13",
                "--config=04-producer"
        );
    }
}
