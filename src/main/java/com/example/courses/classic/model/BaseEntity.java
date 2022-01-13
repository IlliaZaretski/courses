package com.example.courses.classic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(name = "created_at")
    @JsonIgnore
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @JsonIgnore
    private Timestamp updatedAt;

}
