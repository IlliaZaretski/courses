package com.example.courses.classic;

import com.example.courses.classic.exceptions.BookingAlreadyExistsException;
import com.example.courses.classic.exceptions.InvalidBookingRequestException;
import com.example.courses.classic.model.Booking;
import com.example.courses.classic.model.Course;
import com.example.courses.classic.repository.CourseRepository;
import com.example.courses.classic.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ServiceIntegrationTest {

    //@Autowired
    //private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BookingService service;

    @Test
    void testBooking() {
        Course course = courseRepository.save(
                Course.builder().title("Test Course")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(3))
                        .capacity(2).build());
        LocalDate date = LocalDate.now().plusDays(1);

        assertThrows(InvalidBookingRequestException.class, () -> service.createBooking(generateBooking(course, date.minusDays(2))));

        assertThat(service.createBooking(generateBooking(course, date))).isNotNull().extracting(Booking::getId).isNotNull();
        assertThat(service.createBooking(generateBooking(course, date))).isNotNull().extracting(Booking::getId).isNotNull();

        Booking booking = generateBooking(course, LocalDate.now().plusDays(1));
        BookingAlreadyExistsException exception = assertThrows(BookingAlreadyExistsException.class, () -> service.createBooking(booking));
        assertEquals("Booking for Course '" + booking.getCourse().getId() + "' and Date '" + booking.getBookingDate() + "' already completed", exception.getMessage());

        assertThat(service.createBooking(generateBooking(course, date.plusDays(1)))).isNotNull().extracting(Booking::getId).isNotNull();
    }

    private Booking generateBooking(Course course, LocalDate bookingDate) {
        return Booking.builder().memberName("Member " + bookingDate.toString())
                        .bookingDate(bookingDate)
                        .course(course)
                        .build();
    }

}
