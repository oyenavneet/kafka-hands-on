package com.oyenavneet.kafkaplayground.sec09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessorApp {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorApp.class,
                "--section=sec09",
                "--config=03-processor"
        );
    }
}
