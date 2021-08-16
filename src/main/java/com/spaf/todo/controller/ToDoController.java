package com.spaf.todo.controller;

import com.spaf.todo.exception.InvalidTaskException;
import com.spaf.todo.exception.InvalidTaskTypeException;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.ToDo;
import com.spaf.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
