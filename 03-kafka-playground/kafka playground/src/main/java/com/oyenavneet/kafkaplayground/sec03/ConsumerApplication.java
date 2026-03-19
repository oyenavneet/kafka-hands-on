package com.oyenavneet.kafkaplayground.sec03;

import com.oyenavneet.kafkaplayground.sec02.SectionRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,
                "--section=sec03",
                "--config=01-consumer"
        );
    }
}
