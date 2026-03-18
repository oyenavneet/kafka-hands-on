package com.oyenavneet.kafkaplayground.sec02.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class ConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public Function<Flux<String>, Mono<Void>> consumer() {

        return flux -> flux.doOnNext(msg -> logger.info("received: {}", msg))
                .then();
    }
}
