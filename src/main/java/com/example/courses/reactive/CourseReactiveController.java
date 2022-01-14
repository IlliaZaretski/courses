package com.example.courses.reactive;

import com.example.courses.classic.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reactive/courses")
public final class CourseReactiveController {

    @Autowired
    private CourseReactiveRepository repository;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<MongoCourse> getAllCourses() {
        return repository.findAll().delayElements(Duration.ofMillis(500));
    }

    @GetMapping("/{id}")
    Mono<MongoCourse> getCourse(@PathVariable final String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("There is no Course with id " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<MongoCourse> createCourse(@RequestBody final MongoCourse course) {
        return repository.save(course);
    }

    @PutMapping("/{id}")
    Mono<MongoCourse> updateCourse(@RequestBody final MongoCourse newCourse, @PathVariable final String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("There is no Course with id " + id)))
                .flatMap(course -> {
                    course.setTitle(newCourse.getTitle());
                    course.setDescription(newCourse.getDescription());
                    course.setStartDate(newCourse.getStartDate());
                    course.setEndDate(newCourse.getEndDate());
                    course.setCapacity(newCourse.getCapacity());
                    course.setUpdatedAt(LocalDateTime.now());
                    return repository.save(course);
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCourse(@PathVariable final String id) {
        repository.deleteById(id);
    }

}
