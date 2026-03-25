package com.oyenavneet.kafkaplayground.sec14.dto;

public record PhysicalDelivery(
        int orderId,
        String street,
        String city
) {
}
