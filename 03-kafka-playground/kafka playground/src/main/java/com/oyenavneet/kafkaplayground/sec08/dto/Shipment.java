package com.oyenavneet.kafkaplayground.sec08.dto;

public record Shipment(
        int orderId,
        String trackingId
) {
}
