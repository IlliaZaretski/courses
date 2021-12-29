package com.example.courses.reactive;

import com.example.courses.classic.exceptions.EntityNotFoundException;
import com.example.courses.classic.model.Course;
import com.example.courses.classic.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pseudoreactive/courses")
public class CoursePseudoReactiveController {

    @Autowired
    private CourseRepository repository;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Course> getAllCourses() {
        return Flux.fromIterable(repository.findAll()).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    Mono<Course> getCourse(@PathVariable Long id) {
        return Mono.justOrEmpty(repository.findById(id))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("There is no Course with id " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Course> createCourse(@RequestBody Course course) {
        return Mono.just(repository.save(course));
    }

    @PutMapping("/{id}")
    Mono<Course> updateCourse(@RequestBody Course newCourse, @PathVariable Long id) {
        return Mono.justOrEmpty(repository.findById(id))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("There is no Course with id " + id)))
                .flatMap(course -> {
                    course.setTitle(newCourse.getTitle());
                    course.setDescription(newCourse.getDescription());
                    course.setStartDate(newCourse.getStartDate());
                    course.setEndDate(newCourse.getEndDate());
                    course.setCapacity(newCourse.getCapacity());
                    course.setUpdatedAt(Timestamp.from(Instant.now()));
                    return Mono.just(repository.save(course));
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCourse(@PathVariable Long id) {
        repository.deleteById(id);
    }

}