package com.spaf.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.Period;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {

    @Id
    private Long id;
    private boolean completed = false;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;
    private Period durationEstimate;
    private TaskType type;
}
