package com.spaf.todo.model;

import com.spaf.todo.exception.InvalidTaskTypeException;

public enum TaskType {

    WORK("Work"), HOME("Home"), HOBBY("Hobby");

    public final String label;

    private static final String INVALID_TASK_TYPE_MESSAGE = "Invalid task type";

    TaskType(String label) {
        this.label = label;
    }

    public static TaskType valueOfLabel(String label) throws InvalidTaskTypeException {
        for (TaskType t : values()) {
            if (t.label.equals(label)) {
                return t;
            }
        }
        throw new InvalidTaskTypeException(INVALID_TASK_TYPE_MESSAGE);
    }
}
