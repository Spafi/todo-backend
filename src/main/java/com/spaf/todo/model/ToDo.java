package com.spaf.todo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ToDo {

    @Id
    private Long id;
}
