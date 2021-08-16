package com.spaf.todo.service;

import com.spaf.todo.exception.InvalidRetrieveArgumentException;
import com.spaf.todo.exception.InvalidTaskException;
import com.spaf.todo.exception.InvalidTaskTypeException;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.TaskType;
import com.spaf.todo.model.ToDo;
import com.spaf.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoServiceImplementation implements ToDoService {

    private final ToDoRepository repository;

    private final static String INVALID_DURATION_MESSAGE = "Task duration can't be negative!";
    private final static String INVALID_EXPIRATION_MESSAGE = "Task expiration date can't be in the past!";
    private final static String INVALID_SORT_MESSAGE = "Invalid sort parameter!";
    private final static String INVALID_COLUMN_MESSAGE = "Invalid column parameter!";

    private LocalDate convertStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString);
    }

    public ToDo addToDo(AddToDoRequest addRequest) throws InvalidTaskException, InvalidTaskTypeException {

        if (addRequest.getDurationEstimate() < 0) {
            throw new InvalidTaskException(INVALID_DURATION_MESSAGE);
        }

        LocalDate expirationDate = convertStringToLocalDate(addRequest.getExpirationDate());
        LocalDate now = LocalDate.now();
        int timeRemaining = Period.between(now, expirationDate).getDays();

        if (expirationDate.isBefore(now))
            throw new InvalidTaskException(INVALID_EXPIRATION_MESSAGE);

        ToDo toDo = ToDo
                .builder()
                .createdAt(now)
                .name(addRequest.getName())
                .expirationDate(expirationDate)
                .durationEstimate(addRequest.getDurationEstimate())
                .timeRemaining(timeRemaining)
                .type(
                        TaskType.valueOfLabel(addRequest.getType())
                )
                .build();

        return repository.save(toDo);
    }

    public List<ToDo> findAll(String sortDirection, String columnName) throws InvalidRetrieveArgumentException {
        try {
            return repository.findAll(Sort.by(Sort.Direction.valueOf(sortDirection), columnName));
        } catch (IllegalArgumentException e) {
            throw new InvalidRetrieveArgumentException(INVALID_SORT_MESSAGE);
        } catch (PropertyReferenceException e) {
            throw new InvalidRetrieveArgumentException(INVALID_COLUMN_MESSAGE);
        }
    }
}
