package com.spaf.todo.controller;

import com.spaf.todo.exception.*;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.CompleteToDoRequest;
import com.spaf.todo.model.ToDo;
import com.spaf.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/todo")
public class ToDoController {

    private final ToDoService service;

    @PostMapping
    public ResponseEntity<ToDo> addToDo(@RequestBody AddToDoRequest toDo)
            throws InvalidTaskException, InvalidTaskTypeException {
        log.info("New task: {}", toDo);
        return ResponseEntity.ok(service.addToDo(toDo));
    }

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllToDosSortedAndOrdered(
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String columnName) throws InvalidRetrieveArgumentException {

        return ResponseEntity.ok(service.findAll(sortDirection, columnName));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ToDo> completeToDo(
            @PathVariable Long id,
            @RequestBody CompleteToDoRequest request
    )
            throws ToDoNotFoundException, AlreadyCompletedException, InvalidWorkingTimeException {

        return ResponseEntity.ok(service.completeToDo(id, request));
    }
}
