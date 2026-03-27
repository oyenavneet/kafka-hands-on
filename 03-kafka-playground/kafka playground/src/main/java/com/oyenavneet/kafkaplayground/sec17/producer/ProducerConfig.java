package com.oyenavneet.kafkaplayground.sec17.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
public class ProducerConfig {
    private static final Logger logger = LoggerFactory.getLogger(ProducerConfig.class);

    @Bean
    public Supplier<String> producer() {
        var counter = new AtomicInteger(0);
        return () -> {
            var msg = "msg-" + counter.incrementAndGet();
            logger.info("produced: {}", msg);
            return msg;
        };
    }
}
