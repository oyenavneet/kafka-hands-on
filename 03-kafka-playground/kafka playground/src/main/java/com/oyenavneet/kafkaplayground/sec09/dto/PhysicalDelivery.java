package com.oyenavneet.kafkaplayground.sec09.dto;

public record PhysicalDelivery(
        int orderId,
        String street,
        String city
) {
}
