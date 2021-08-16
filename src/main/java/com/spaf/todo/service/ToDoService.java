package com.spaf.todo.service;

import com.spaf.todo.exception.InvalidRetrieveArgumentException;
import com.spaf.todo.exception.InvalidTaskException;
import com.spaf.todo.exception.InvalidTaskTypeException;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.ToDo;

import java.util.List;

public interface ToDoService {
    ToDo addToDo(AddToDoRequest toDo) throws InvalidTaskException, InvalidTaskTypeException;
    List<ToDo> findAll(String sortDirection, String columnName) throws InvalidRetrieveArgumentException;
}
