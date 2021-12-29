package com.example.courses.reactive;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CourseReactiveController.class)
public class CourseReactiveControllerTest {

    @MockBean
    CourseReactiveRepository repository;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    private void mutateWebClient() {
        webClient = webClient.mutate().baseUrl("http://localhost:8080/reactive/courses").build();
    }

    @Test
    void testGetAllCourses() {
        MongoCourse course = MongoCourse.builder().title("Test Course")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .capacity(2).id("123").build();
        List<MongoCourse> list = new ArrayList<>();
        list.add(course);
        Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(list));

        webClient.get().uri("/")
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MongoCourse.class)
                .hasSize(1)
                .contains(course);

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void testCreateCourse() {
        MongoCourse course = MongoCourse.builder().title("Test Course")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .capacity(2).id("123").build();

        Mockito.when(repository.save(course)).thenReturn(Mono.just(course));

        webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(course))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(repository, times(1)).save(course);
    }

    @Test
    void testGetCourseById() {
        MongoCourse course = MongoCourse.builder().title("Test")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .capacity(100).id("123").build();

        Mockito.when(repository.findById("123"))
                .thenReturn(Mono.just(course));

        webClient.get().uri("/{id}", "123")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isNotEmpty()
                .jsonPath("$.id").isEqualTo("123")
                .jsonPath("$.title").isEqualTo("Test")
                .jsonPath("$.capacity").isEqualTo(100);

        Mockito.verify(repository, times(1)).findById("123");
    }

    @Test
    void testDeleteCourse() {
        Mono<Void> voidReturn = Mono.empty();
        Mockito.when(repository.deleteById("123")).thenReturn(voidReturn);

        webClient.delete().uri("/{id}", "123")
                .exchange()
                .expectStatus().isNoContent();
    }
}
