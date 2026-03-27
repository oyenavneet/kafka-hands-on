package com.oyenavneet.kafkaplayground.sec16.consumer;

import com.oyenavneet.kafkaplayground.sec16.exceptions.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public void saveOrder(Integer orderId) {
        if (orderId > 5) {
            this.simulateTransientFailure();
        }
        logger.info("Order {} saved successfully", orderId);
    }

    private void simulateTransientFailure() {
        int random = ThreadLocalRandom.current().nextInt(1, 11);
        logger.info("random: {}", random);

        if (random < 8) {
            logger.warn("service unavailable");
            throw new ServiceUnavailableException();
        }
    }
}
