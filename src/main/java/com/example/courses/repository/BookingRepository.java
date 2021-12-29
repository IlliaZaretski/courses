package com.example.courses.repository;

import com.example.courses.model.Booking;
import com.example.courses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByMemberName(String memberName);

    List<Booking> findByCourse(Course course);
}
