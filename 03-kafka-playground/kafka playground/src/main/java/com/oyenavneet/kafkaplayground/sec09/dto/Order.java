package com.oyenavneet.kafkaplayground.sec09.dto;

public record Order(
        int id,
        int customerId,
        int amount,
        ProductType productType
) {
}
