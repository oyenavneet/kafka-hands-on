package com.oyenavneet.kafkaplayground.sec01.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public Consumer<String> consumer() {

        return msg -> logger.info("Receiver in c1: {}", msg);
    }

    @Bean
    public Consumer<String> consumer1() {

        return msg -> logger.info("Receiver in c2: {}", msg);
    }
}
