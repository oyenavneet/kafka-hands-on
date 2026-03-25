package com.oyenavneet.kafkaplayground.sec13.dto;

public record Order(
        int id,
        int customerId,
        int amount,
        ProductType productType
) {
}
