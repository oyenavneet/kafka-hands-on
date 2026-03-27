package com.oyenavneet.kafkaplayground.sec16.consumer;

import com.oyenavneet.kafkaplayground.sec16.exceptions.InputValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);
    private final OrderService orderService;

    public ConsumerConfig(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public Consumer<Message<Integer>> consumer() {
        return this::validateAndProcess;
    }

    private void validateAndProcess(Message<Integer> message) {
        logger.info("message: {}", message);
        var orderId = message.getPayload();
        if (orderId < 1) {
            throw new InputValidationException(orderId);
        }
        this.orderService.saveOrder(orderId);
    }
}
