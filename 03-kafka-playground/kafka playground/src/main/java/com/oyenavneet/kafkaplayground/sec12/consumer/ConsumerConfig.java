package com.oyenavneet.kafkaplayground.sec12.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public Consumer<List<String>> consumer() {
        var counter = new AtomicInteger(0);
        return list -> {
            logger.info("Batch received: {}, Total processed: {}", list.size(), counter.addAndGet(list.size()));
        };
    }

}