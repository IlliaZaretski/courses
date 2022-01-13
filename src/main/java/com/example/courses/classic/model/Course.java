package com.example.courses.classic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "courses")
public class Course extends BaseEntity {

	@NotBlank
	private String title;

	private String description;

	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NonNull
	private LocalDate startDate;

	@Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NonNull
	private LocalDate endDate;

	@Positive
	@NotNull
	private Integer capacity;

	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Set<Booking> bookings;

}
