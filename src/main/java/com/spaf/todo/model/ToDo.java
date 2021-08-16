package com.spaf.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean completed = false;
    private String name;
    private LocalDate createdAt;
    private LocalDate expirationDate;
    private int durationEstimate;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Transient
    private int timeRemaining;

    @PostLoad
    public void postLoad() {
        timeRemaining = Period.between(LocalDate.now(), expirationDate).getDays();
    }

}
