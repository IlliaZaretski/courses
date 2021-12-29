package com.example.courses.controller.reactive;

import com.example.courses.model.Course;
import com.example.courses.repository.CourseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes-react")
public class CourseReactiveController {

    @Autowired
    private CourseRepository repository;

    @GetMapping
    List<Course> getCourses() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Course> getCourse(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    Course createCourse(@RequestBody Course course) {
        return repository.save(course);
    }

    @PutMapping("/{id}")
    Course updateCourse(@RequestBody Course newCourse, @PathVariable Long id) {
        return repository.findById(id)
                .map(course -> {
                    BeanUtils.copyProperties(course, newCourse,  "id,", "bookings");
                    return repository.save(course);
                })
                .orElseGet(() -> repository.save(newCourse));
    }

    @DeleteMapping
    void deleteCourse(Long id) {
        repository.deleteById(id);
    }

}
