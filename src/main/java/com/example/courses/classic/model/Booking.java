package com.example.courses.classic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "bookings")
public class Booking extends BaseEntity {

	@Column(name = "member_name")
	@NotEmpty
	private String memberName;

	@Column(name = "booking_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bookingDate;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "course_id", nullable = false)
	@JsonIncludeProperties({"id", "title"})
	private Course course;

	@Column(name = "updated_at")
	@JsonIgnore
	private Timestamp updatedAt;

}
