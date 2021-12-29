package com.example.courses.classic.exceptions;

import com.example.courses.classic.model.Booking;

public class BookingAlreadyExistsException extends RuntimeException {

    public BookingAlreadyExistsException(final Booking booking) {
        super("Booking for Course '" + booking.getCourse().getId() + "' and Date '" + booking.getBookingDate() + "' already completed");
    }
}