package com.example.courses.controller.classic;

import com.example.courses.controller.exceptions.EntityNotFoundException;
import com.example.courses.model.Booking;
import com.example.courses.model.Course;
import com.example.courses.repository.CourseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository repository;

    @GetMapping
    List<Course> getCourses() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Course getCourse(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Course with id " + id));
    }

    @GetMapping("/{id}/bookings")
    List<Booking> getBookingsForCourse(@PathVariable Long id) {
        Course course = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Course with id " + id));
        return List.copyOf(course.getBookings());
    }

    @PostMapping
    Course createCourse(@RequestBody Course course) {
        return repository.save(course);
    }

    @PutMapping("/{id}")
    Course updateCourse(@RequestBody Course newCourse, @PathVariable Long id) {
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
                .orElseGet(() -> repository.save(newCourse));
    }

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
