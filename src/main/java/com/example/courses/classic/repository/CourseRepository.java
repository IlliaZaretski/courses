package com.example.courses.classic.repository;

import com.example.courses.classic.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTitle(String title);

    @Query("SELECT c FROM Course c WHERE c.startDate <= ?1 AND c.endDate >= ?1")
    List<Course> findByDate(LocalDate date);
}
