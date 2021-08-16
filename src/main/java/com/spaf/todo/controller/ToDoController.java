package com.spaf.todo.controller;

import com.spaf.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/v1/todo")
public class ToDoController {

    private final ToDoService service;


}
