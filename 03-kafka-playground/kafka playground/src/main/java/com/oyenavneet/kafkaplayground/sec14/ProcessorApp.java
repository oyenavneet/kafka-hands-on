package com.oyenavneet.kafkaplayground.sec14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessorApp {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorApp.class,
                "--section=sec14",
                "--config=03-processor",
                "--processing.mode=ordered"
        );
    }
}
