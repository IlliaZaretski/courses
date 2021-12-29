package com.example.courses.classic.exceptions;

public class InvalidBookingRequestException extends RuntimeException {

    public InvalidBookingRequestException(final String message) {
        super(message);
    }
}