package com.spaf.todo.service;

import com.spaf.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository repository;
}
