package com.oyenavneet.kafkaplayground.sec03;

import com.oyenavneet.kafkaplayground.sec02.SectionRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,
                "--section=sec03",
                "--config=04-message-producer"
        );
    }
}
