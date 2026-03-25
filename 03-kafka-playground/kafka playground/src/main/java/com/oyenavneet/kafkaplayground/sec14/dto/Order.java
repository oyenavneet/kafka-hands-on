package com.oyenavneet.kafkaplayground.sec14.dto;

public record Order(
        int id,
        int customerId,
        int amount,
        ProductType productType
) {
}
