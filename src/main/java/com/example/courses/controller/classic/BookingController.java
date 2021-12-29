package com.example.courses.controller.classic;

import com.example.courses.controller.exceptions.EntityNotFoundException;
import com.example.courses.controller.exceptions.InvalidBookingRequestException;
import com.example.courses.model.Booking;
import com.example.courses.model.Course;
import com.example.courses.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingRepository repository;

    @GetMapping
    List<Booking> getAllBookings() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Booking getBooking(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Booking with id " + id));
    }

    @GetMapping("/course/{courseId}")
    List<Booking> getBookingsByCourseId(@PathVariable("courseId") Long courseId) {
        Course course = new Course();
        course.setId(courseId);
        return repository.findByCourse(course);
    }

    @PostMapping
    Booking createBooking(@RequestBody Booking booking) {
        if (booking.getCourse() == null) throw new InvalidBookingRequestException("Course is mandatory for Booking");
        return repository.save(booking);
    }

    @PutMapping("/{id}")
    Booking updateBooking(@RequestBody Booking newBooking, @PathVariable Long id) {
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
    public void deleteBooking(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
