package com.oyenavneet.kafkaplayground.sec07.dto;

public record Order(
        int id,
        int customerId,
        int amount,
        ProductType productType
) {
}
