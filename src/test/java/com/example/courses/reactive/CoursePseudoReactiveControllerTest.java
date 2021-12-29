package com.example.courses.reactive;

import com.example.courses.classic.model.Course;
import com.example.courses.classic.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CoursePseudoReactiveController.class)
public class CoursePseudoReactiveControllerTest {

    @MockBean
    CourseRepository repository;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    private void mutateWebClient() {
        webClient = webClient.mutate().baseUrl("http://localhost:8080/pseudoreactive/courses").build();
    }

    @Test
    void testGetAllCourses() {
        Course course = Course.builder().title("Test Course")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .capacity(2).build();
        List<Course> list = new ArrayList<>();
        list.add(course);
        Mockito.when(repository.findAll()).thenReturn(list);

        webClient.get().uri("/")
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Course.class)
                .hasSize(1)
                .contains(course);

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void testCreateCourse() {
        Course course = Course.builder().title("Test Course")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .capacity(2).build();

        Mockito.when(repository.save(course)).thenReturn(course);

        webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(course))
                .exchange()
                .expectStatus().isCreated()
                .expectBody();

        Mockito.verify(repository, times(1)).save(course);
    }

    @Test
    void testGetCourseById() {
        Course course = Course.builder().title("Test")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .capacity(100).build();
        course.setId(123L);

        Mockito.when(repository.findById(123L)).thenReturn(java.util.Optional.of(course));

        webClient.get().uri("/{id}", "123")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isNotEmpty()
                .jsonPath("$.id").isEqualTo(course.getId())
                .jsonPath("$.title").isEqualTo(course.getTitle())
                .jsonPath("$.capacity").isEqualTo(course.getCapacity());

        Mockito.verify(repository, times(1)).findById(123L);
    }

    @Test
    void testDeleteCourse() {
        CourseRepository mock = mock(CourseRepository.class);
        Mockito.doNothing().when(mock).deleteById(123L);

        webClient.delete().uri("/{id}", "123")
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(repository, times(1)).deleteById(123L);
    }
}
