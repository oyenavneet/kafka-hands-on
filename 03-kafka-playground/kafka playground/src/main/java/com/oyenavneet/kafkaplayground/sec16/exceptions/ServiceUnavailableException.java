package com.oyenavneet.kafkaplayground.sec16.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    private static final String MESSAGE = "Service unavailable";

    public ServiceUnavailableException() {
        super(MESSAGE);
    }
}
