package com.example.courses.classic.controller;

import com.example.courses.classic.exceptions.EntityNotFoundException;
import com.example.courses.classic.model.Booking;
import com.example.courses.classic.model.Course;
import com.example.courses.classic.repository.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/courses")
public final class CourseController {

    @Autowired
    private CourseRepository repository;

    @GetMapping
    List<Course> getCourses() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Course getCourse(@PathVariable final Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Course with id " + id));
    }

    @Operation(summary = "Get all bookings by Course id")
    @GetMapping("/{id}/bookings")
    List<Booking> getBookingsForCourse(@PathVariable final Long id) {
        Course course = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Course with id " + id));
        return List.copyOf(course.getBookings());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Course createCourse(@RequestBody final Course course) {
        return repository.save(course);
    }

    @PutMapping("/{id}")
    Course updateCourse(@RequestBody final Course newCourse, @PathVariable final Long id) {
        return repository.findById(id)
                .map(course -> {
                    course.setTitle(newCourse.getTitle());
                    course.setDescription(newCourse.getDescription());
                    course.setStartDate(newCourse.getStartDate());
                    course.setEndDate(newCourse.getEndDate());
                    course.setCapacity(newCourse.getCapacity());
                    course.setUpdatedAt(Timestamp.from(Instant.now()));
                    return repository.save(course);
                })
                .orElseThrow(() -> new EntityNotFoundException("There is no Course with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCourse(@PathVariable final Long id) {
        repository.deleteById(id);
    }

}
