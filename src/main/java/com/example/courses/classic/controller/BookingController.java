package com.example.courses.classic.controller;

import com.example.courses.classic.exceptions.EntityNotFoundException;
import com.example.courses.classic.repository.BookingRepository;
import com.example.courses.classic.service.BookingService;
import com.example.courses.classic.model.Booking;
import com.example.courses.classic.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public final class BookingController {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    List<Booking> getAllBookings() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Booking getBooking(@PathVariable final Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Booking with id " + id));
    }

    @GetMapping("/course/{courseId}")
    List<Booking> getBookingsByCourseId(@PathVariable("courseId") final Long courseId) {
        Course course = new Course();
        course.setId(courseId);
        return repository.findByCourse(course);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Booking createBooking(@Valid @RequestBody final Booking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Booking updateBooking(@RequestBody final Booking newBooking, @PathVariable final Long id) {
        return repository.findById(id)
                .map(booking -> {
                    booking.setMemberName(newBooking.getMemberName());
                    booking.setBookingDate(newBooking.getBookingDate());
                    booking.setUpdatedAt(Timestamp.from(Instant.now()));
                    return repository.save(booking);
                })
                .orElseGet(() -> repository.save(newBooking));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable final Long id) {
        repository.deleteById(id);
    }

}
