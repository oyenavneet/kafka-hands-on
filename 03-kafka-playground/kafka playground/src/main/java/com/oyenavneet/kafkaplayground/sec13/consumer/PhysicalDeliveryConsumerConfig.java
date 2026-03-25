package com.oyenavneet.kafkaplayground.sec13.consumer;

import com.oyenavneet.kafkaplayground.sec13.dto.PhysicalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PhysicalDeliveryConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(PhysicalDeliveryConsumerConfig.class);

    @Bean
    public Consumer<PhysicalDelivery> physicalConsumer() {
        return msg -> logger.info("received: {}", msg);
    }
}
