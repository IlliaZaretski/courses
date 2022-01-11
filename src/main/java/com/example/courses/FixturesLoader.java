package com.example.courses;

import com.example.courses.classic.model.Booking;
import com.example.courses.classic.model.Course;
import com.example.courses.classic.repository.BookingRepository;
import com.example.courses.classic.repository.CourseRepository;
import com.example.courses.reactive.CourseReactiveRepository;
import com.example.courses.reactive.MongoCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("main")
class FixturesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixturesLoader.class);

    @Bean
    CommandLineRunner initDatabase(final CourseRepository courseRepository,
                                   final BookingRepository bookingRepository,
                                   final CourseReactiveRepository courseReactiveRepository) {

        fillClassicDatabase(courseRepository, bookingRepository);

        fillReactiveDatabase(courseReactiveRepository);

        return args -> {
            courseRepository.findAll().forEach(course -> LOGGER.info("Preloaded in MySQL: " + course));
            bookingRepository.findAll().forEach(booking -> LOGGER.info("Preloaded in MySQL: " + booking));
            courseReactiveRepository.findAll().toIterable().forEach(course -> LOGGER.info("Preloaded in Mongo: " + course));
        };
    }

    private void fillClassicDatabase(final CourseRepository courseRepository, final BookingRepository bookingRepository) {
        if (!courseRepository.findByTitle("Test Course 1").isEmpty()) {
            LOGGER.info("Fixtures already preloaded in MySQL");
            return;
        }

        Course course1 = courseRepository.save(Course.builder().title("Test Course 1").description("Test Desc 1")
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(10))
                .capacity(10).build());
        bookingRepository.save(Booking.builder().memberName("Member 1").bookingDate(LocalDate.now().plusDays(2)).course(course1).build());
        bookingRepository.save(Booking.builder().memberName("Member 2").bookingDate(LocalDate.now().plusDays(5)).course(course1).build());

        Course course2 = courseRepository.save(Course.builder().title("Test Course 2").description("Test Desc 2")
                .startDate(LocalDate.now().plusDays(10)).endDate(LocalDate.now().plusDays(30))
                .capacity(7).build());
        bookingRepository.save(Booking.builder().memberName("Member 3").bookingDate(LocalDate.now().plusDays(20)).course(course2).build());
        bookingRepository.save(Booking.builder().memberName("Member 4").bookingDate(LocalDate.now().plusDays(22)).course(course2).build());
        bookingRepository.save(Booking.builder().memberName("Member 5").bookingDate(LocalDate.now().plusDays(11)).course(course2).build());
    }

    private void fillReactiveDatabase(final CourseReactiveRepository courseReactiveRepository) {
        if (courseReactiveRepository.findByTitle("Test Course 1").toIterable().iterator().hasNext()) {
            LOGGER.info("Fixtures already preloaded in Mongo");
            return;
        }
        int capacity = 10;
        List<MongoCourse> list = new ArrayList<>(capacity);
        for (int i = 1; i <= capacity; i++) {
            list.add(MongoCourse.builder().title("Test Course " + i).description("Test Desc " + i)
                    .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(i))
                    .capacity(i).build());
        }
        courseReactiveRepository.saveAll(list).collectList().block();
    }
}

//curl -X GET localhost:8080/courses
//curl -X GET localhost:8080/courses/24/bookings
//curl -X POST localhost:8080/courses -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "startDate": "2021-12-31", "endDate": "2021-12-31", "capacity": "10"}'
//curl -X PUT localhost:8080/courses/23 -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "startDate": "2021-12-31", "endDate": "2021-12-31", "capacity": "5"}'
//curl -X POST localhost:8080/bookings -H 'Content-type:application/json' -d '{"memberName": "Samwise Gamgee", "bookingDate": "2021-12-31", "courseId": "26"}'
//curl -X DELETE localhost:8080/courses/21
//curl -X DELETE localhost:8080/bookings/32
