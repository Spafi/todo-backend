package com.spaf.todo.service;

import com.spaf.todo.exception.*;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.CompleteToDoRequest;
import com.spaf.todo.model.ToDo;

import java.util.List;

public interface ToDoService {

    ToDo addToDo(AddToDoRequest toDo) throws InvalidTaskException, InvalidTaskTypeException;
    List<ToDo> findAll(String sortDirection, String columnName) throws InvalidRetrieveArgumentException;
    ToDo findById(Long id) throws ToDoNotFoundException;
    ToDo completeToDo(Long id, CompleteToDoRequest request) throws ToDoNotFoundException, AlreadyCompletedException, InvalidWorkingTimeException;

    void deleteToDo(Long id) throws ToDoNotFoundException, InvalidTaskException;
}
