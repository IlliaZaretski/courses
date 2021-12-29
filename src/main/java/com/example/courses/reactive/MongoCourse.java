package com.example.courses.reactive;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document (collection = "courses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MongoCourse implements Serializable {

	@Id
	private String id;

	private String title;

	private String description;

	private LocalDate startDate;

	private LocalDate endDate;

	private Integer capacity;

	private LocalDateTime createdAt = LocalDateTime.now();

	private LocalDateTime updatedAt = LocalDateTime.now();

}
