package com.oyenavneet.kafkaplayground.sec12.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Component
public class ProducerConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProducerConfig.class);
    private static final String DEMO_OUT = "demo-out";
    private final StreamBridge streamBridge;

    public ProducerConfig(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 1_000_000; i++) {
            this.streamBridge.send(DEMO_OUT, "msg-" + i);
            if (i % 1_000 == 0) {
                logger.info("Total message produced: {}", i);
                Thread.sleep((Duration.ofMillis(10)));
            }
        }
    }
}