package com.spaf.todo.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
}
