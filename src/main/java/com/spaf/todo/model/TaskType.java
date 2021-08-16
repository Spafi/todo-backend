package com.spaf.todo.model;

public enum TaskType {

    WORK("Work"), HOME("Home"), HOBBY("Hobby");

    public final String type;

    TaskType(String type) {
        this.type = type;
    }
}
