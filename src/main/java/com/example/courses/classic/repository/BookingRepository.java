package com.example.courses.classic.repository;

import com.example.courses.classic.model.Booking;
import com.example.courses.classic.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByMemberName(String memberName);

    List<Booking> findByCourse(Course course);

    int countByCourseAndBookingDate(Course course, LocalDate bookingDate);
}
