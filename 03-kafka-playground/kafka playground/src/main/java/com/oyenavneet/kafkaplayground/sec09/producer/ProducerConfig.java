package com.oyenavneet.kafkaplayground.sec09.producer;

import com.oyenavneet.kafkaplayground.sec09.dto.Order;
import com.oyenavneet.kafkaplayground.sec09.dto.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
public class ProducerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ProducerConfig.class);


    @Bean
    public Supplier<Order> producer() {
        var counter = new AtomicInteger(0);

        return () -> {
            var id = counter.incrementAndGet();
            var productType = id % 2 == 0 ? ProductType.PHYSICAL : ProductType.DIGITAL;
            var order = new Order(id, id, ThreadLocalRandom.current().nextInt(10, 2000), productType);
            logger.info("Producer order {} ", order);
            return order;
        };
    }


}
