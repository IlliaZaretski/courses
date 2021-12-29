package com.example.courses;

import com.example.courses.model.Booking;
import com.example.courses.model.Course;
import com.example.courses.repository.BookingRepository;
import com.example.courses.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
class FixturesLoader {

    private static final Logger log = LoggerFactory.getLogger(FixturesLoader.class);

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository, BookingRepository bookingRepository) {

        if (!courseRepository.findByTitle("Test Course 1").isEmpty()) return args -> {
            log.info("Fixtures already preloaded");
        };

        Course course1 = Course.builder().title("Test Course 1").description("Test Desc 1")
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(10))
                .capacity(10).build();
        Booking booking1 = Booking.builder().memberName("Member 1").bookingDate(LocalDate.now().plusDays(2)).course(course1).build();
        Booking booking2 = Booking.builder().memberName("Member 2").bookingDate(LocalDate.now().plusDays(5)).course(course1).build();

        Course course2 = Course.builder().title("Test Course 2").description("Test Desc 2")
                .startDate(LocalDate.now().plusDays(10)).endDate(LocalDate.now().plusDays(30))
                .capacity(7).build();
        Booking booking3 = Booking.builder().memberName("Member 3").bookingDate(LocalDate.now().plusDays(20)).course(course2).build();
        Booking booking4 = Booking.builder().memberName("Member 4").bookingDate(LocalDate.now().plusDays(22)).course(course2).build();
        Booking booking5 = Booking.builder().memberName("Member 5").bookingDate(LocalDate.now().plusDays(11)).course(course2).build();

        return args -> {
            log.info("Preloading " + courseRepository.save(course1));
            log.info("Preloading " + bookingRepository.save(booking1));
            log.info("Preloading " + bookingRepository.save(booking2));
            log.info("Preloading " + courseRepository.save(course2));
            log.info("Preloading " + bookingRepository.save(booking3));
            log.info("Preloading " + bookingRepository.save(booking4));
            log.info("Preloading " + bookingRepository.save(booking5));
        };
    }
}

//curl -X GET localhost:8080/courses
//curl -X GET localhost:8080/courses/24/bookings
//curl -X POST localhost:8080/courses -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "startDate": "2021-12-31", "endDate": "2021-12-31", "capacity": "10"}'
//curl -X PUT localhost:8080/courses/23 -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "startDate": "2021-12-31", "endDate": "2021-12-31", "capacity": "5"}'
//curl -X POST localhost:8080/bookings -H 'Content-type:application/json' -d '{"memberName": "Samwise Gamgee", "bookingDate": "2021-12-31", "courseId": "26"}'
//curl -X DELETE localhost:8080/courses/21
//curl -X DELETE localhost:8080/bookings/32