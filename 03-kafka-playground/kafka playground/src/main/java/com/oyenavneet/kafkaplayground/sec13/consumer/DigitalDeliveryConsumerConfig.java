package com.oyenavneet.kafkaplayground.sec13.consumer;

import com.oyenavneet.kafkaplayground.sec13.dto.DigitalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class DigitalDeliveryConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DigitalDeliveryConsumerConfig.class);

    @Bean
    public Consumer<DigitalDelivery> digitalConsumer() {
        return msg -> logger.info("received: {}", msg);
    }
}
