package com.oyenavneet.kafkaplayground.sec19.dto;

public record PhysicalDelivery(
        int orderId,
        String street,
        String city
) {
}
