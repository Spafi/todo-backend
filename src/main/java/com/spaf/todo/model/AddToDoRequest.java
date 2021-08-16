package com.spaf.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToDoRequest {
    private String name;
    private String expirationDate;
    private int durationEstimate;
    private String type;
}
