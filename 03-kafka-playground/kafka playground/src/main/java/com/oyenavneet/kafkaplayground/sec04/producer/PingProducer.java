package com.oyenavneet.kafkaplayground.sec04.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Component
public class PingProducer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PingProducer.class);
    private static final String PING_OUT = "ping-out"; // configured in 02-producer.yaml for binding
    private final StreamBridge streamBridge;

    public PingProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    @Override
    public void run(String... args) throws Exception {
        var process = new ProcessBuilder("ping", "-n", "10", "google.com")
                .redirectErrorStream(true)
                .start();

        try (var reader = process.inputReader()) {
            reader.lines()
                    .forEach(line -> {
                        logger.info("sending:{}", line);
                        this.streamBridge.send(PING_OUT, line);
                    });
        }

    }
}
