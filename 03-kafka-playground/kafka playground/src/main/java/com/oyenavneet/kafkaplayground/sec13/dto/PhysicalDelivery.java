package com.oyenavneet.kafkaplayground.sec13.dto;

public record PhysicalDelivery(
        int orderId,
        String street,
        String city
) {
}
