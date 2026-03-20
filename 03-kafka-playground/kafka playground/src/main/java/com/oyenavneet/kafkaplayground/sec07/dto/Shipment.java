package com.oyenavneet.kafkaplayground.sec07.dto;

public record Shipment(
        int orderId,
        String trackingId
) {
}
