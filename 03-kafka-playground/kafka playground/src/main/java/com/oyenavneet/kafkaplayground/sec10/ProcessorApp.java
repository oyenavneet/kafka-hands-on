package com.oyenavneet.kafkaplayground.sec10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessorApp {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorApp.class,
                "--section=sec10",
                "--config=04-processor"
        );
    }
}
