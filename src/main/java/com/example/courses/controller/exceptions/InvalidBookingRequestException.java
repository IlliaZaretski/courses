package com.example.courses.controller.exceptions;

public class InvalidBookingRequestException extends RuntimeException {

    public InvalidBookingRequestException(final String message) {
        super(message);
    }
}