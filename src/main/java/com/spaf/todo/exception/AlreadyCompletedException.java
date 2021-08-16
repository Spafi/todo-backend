package com.spaf.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlreadyCompletedException extends Exception {
    private final String message;
}
