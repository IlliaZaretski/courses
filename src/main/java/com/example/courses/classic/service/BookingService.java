package com.example.courses.classic.service;

import com.example.courses.classic.exceptions.BookingAlreadyExistsException;
import com.example.courses.classic.exceptions.EntityNotFoundException;
import com.example.courses.classic.exceptions.InvalidBookingRequestException;
import com.example.courses.classic.repository.BookingRepository;
import com.example.courses.classic.repository.CourseRepository;
import com.example.courses.classic.model.Booking;
import com.example.courses.classic.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class BookingService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(final Booking booking) {
        if (booking.getCourse() == null || booking.getCourse().getId() == null) throw new InvalidBookingRequestException("Course is mandatory for Booking");

        Course course = courseRepository.findById(booking.getCourse().getId())
                .orElseThrow(() -> new EntityNotFoundException("There is no Course with id " + booking.getCourse().getId()));

        if (booking.getBookingDate().isBefore(course.getStartDate())
                || booking.getBookingDate().isAfter(course.getEndDate()))
            throw new InvalidBookingRequestException("Booking date '" + booking.getBookingDate() + "' is out of Course dates: '" + course.getStartDate() + "' - '" + course.getEndDate() + "'");

        if (bookingRepository.countByCourseAndBookingDate(booking.getCourse(), booking.getBookingDate()) >= course.getCapacity())
            throw new BookingAlreadyExistsException(booking);

        return bookingRepository.save(booking);
    }
}
