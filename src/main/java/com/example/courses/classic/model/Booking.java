package com.example.courses.classic.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @Column(name = "member_name")
    @NotEmpty
    private String memberName;

    @Column(name = "booking_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@NonNull
    private LocalDate bookingDate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIncludeProperties({"id", "title"})
    private Course course;

}
