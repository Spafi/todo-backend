package com.spaf.todo.service;

import com.spaf.todo.exception.InvalidTaskException;
import com.spaf.todo.exception.InvalidTaskTypeException;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.TaskType;
import com.spaf.todo.model.ToDo;
import com.spaf.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ToDoServiceImplementation implements ToDoService {

    private final ToDoRepository repository;

    private final static String INVALID_DURATION_MESSAGE = "Task duration can't be negative!";
    private final static String INVALID_EXPIRATION_MESSAGE = "Task expiration date can't be in the past!";

    private LocalDate convertStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString);
    }

    public ToDo addToDo(AddToDoRequest addRequest) throws InvalidTaskException, InvalidTaskTypeException {

        if (addRequest.getDurationEstimate() < 0) {
            throw new InvalidTaskException(INVALID_DURATION_MESSAGE);
        }

        LocalDate expirationDate = convertStringToLocalDate(addRequest.getExpirationDate());
        LocalDate now = LocalDate.now();

        if(expirationDate.isBefore(now))
            throw new InvalidTaskException(INVALID_EXPIRATION_MESSAGE);

        ToDo toDo = ToDo
                .builder()
                .createdAt(now)
                .name(addRequest.getName())
                .expirationDate(expirationDate)
                .durationEstimate(addRequest.getDurationEstimate())
                .type(
                        TaskType.valueOfLabel(addRequest.getType())
                )
                .build();
        return repository.save(toDo);
    }
}
