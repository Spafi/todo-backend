package com.spaf.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

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
    private LocalDate finishedAt;
    private int durationEstimate;
    private int actualWorkedTime;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Transient
    private Long timeRemaining;

    @PostLoad
    public void postLoad() {
        if (!isCompleted())
            timeRemaining = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
    }

}
