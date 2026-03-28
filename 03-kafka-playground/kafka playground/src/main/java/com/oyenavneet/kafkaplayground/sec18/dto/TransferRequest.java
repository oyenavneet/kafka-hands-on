package com.oyenavneet.kafkaplayground.sec18.dto;

public record TransferRequest(
        String fromAccount,
        String toAccount,
        Integer amount
) {
}
