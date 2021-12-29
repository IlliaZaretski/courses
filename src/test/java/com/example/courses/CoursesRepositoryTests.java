package com.example.courses;

import com.example.courses.model.Booking;
import com.example.courses.model.Course;
import com.example.courses.repository.BookingRepository;
import com.example.courses.repository.CourseRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CoursesRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    @AfterEach
    private void cleanDatabase() {
        courseRepository.deleteAllInBatch();
        bookingRepository.deleteAllInBatch();
    }

    @Test
    void testCourses() {
        Course course = createCourse(1);
        Course course2 = createCourse(3);

        assertThat(courseRepository.findByTitle(course2.getTitle())).isNotEmpty().hasSize(1)
                .extracting(Course::getId).containsExactly(course2.getId());

        assertThat(courseRepository.findByDate(LocalDate.now())).isNotEmpty().hasSize(2)
                .extracting(Course::getId).containsExactly(course.getId(), course2.getId());

        assertThat(courseRepository.findByDate(LocalDate.now().plusDays(2))).isNotEmpty().hasSize(1)
                .extracting(Course::getId).containsExactly( course2.getId());

        courseRepository.delete(course);
        assertThat(courseRepository.findById(course.getId())).isNotPresent();
    }

    @Test
    void testBookings() {
		Course course = createCourse(10);

        Booking b1 = createBooking(course, 1);
        Booking b2 = createBooking(course, 2);
		assertThat(bookingRepository.findByMemberName(b1.getMemberName())).extracting(Booking::getMemberName).containsOnly(b1.getMemberName());
        assertThat(bookingRepository.findByCourse(course)).isNotEmpty().hasSize(2).extracting(Booking::getId).containsExactly(b1.getId(), b2.getId());

        bookingRepository.delete(b1);
		assertThat(bookingRepository.findById(b1.getId())).isNotPresent();

        courseRepository.delete(course);
        assertThat(courseRepository.findById(course.getId())).isNotPresent();
        assertThat(bookingRepository.findById(b2.getId())).isNotPresent();
    }

    private Course createCourse(int order) {
        Course course = courseRepository.save(
                Course.builder().title("Test Course " + order).description("Test Desc " + order)
                        .startDate(LocalDate.now()).endDate(LocalDate.now()
                                .plusDays(order)).capacity(order).build());

        assertThat(course).isNotNull().extracting(Course::getId).isNotNull();
        assertThat(courseRepository.findById(course.getId())).isPresent().get().isNotNull().isEqualTo(course);
        return course;
    }

    private Booking createBooking(Course course, int order) {
        Booking booking = bookingRepository.save(
                Booking.builder().memberName("Member " + order)
                        .bookingDate(LocalDate.now().plusDays(order))
                        .course(course)
                        .build());
        assertThat(booking).isNotNull().extracting(Booking::getId).isNotNull();
        assertThat(bookingRepository.findById(booking.getId())).isPresent().get().isNotNull().isEqualTo(booking);
        return booking;
    }

}
