package com.oyenavneet.kafkaplayground.sec07.consumer;

import com.oyenavneet.kafkaplayground.sec07.dto.Notification;
import com.oyenavneet.kafkaplayground.sec07.dto.Payment;
import com.oyenavneet.kafkaplayground.sec07.dto.Shipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public Consumer<Payment> paymentConsumer() {
        return this::logReceived;
    }

    @Bean
    public Consumer<Shipment> shipmentConsumer() {
        return this::logReceived;
    }

    @Bean
    public Consumer<Notification> notificationConsumer() {
        return this::logReceived;
    }

    private void logReceived(Object payload) {
        logger.info("received : {}", payload);
    }
}
