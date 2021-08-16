package com.spaf.todo.service;

import com.spaf.todo.exception.*;
import com.spaf.todo.model.AddToDoRequest;
import com.spaf.todo.model.CompleteToDoRequest;
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
    private final static String NOT_FOUND_MESSAGE = "Task not found!";
    private final static String ALREADY_COMPLETED_MESSAGE = "Task already completed!";
    private final static String EXCEDED_MAX_WORK_TIME_MESSAGE =
            "Working time cannot exceed time passed since task is created!";
    private final static String NEGATIVE_WORK_TIME_MESSAGE = "Worked time can't be negative!";
    private final static String CANT_DELETE_COMPLETED_MESSAGE = "Can't delete a completed task!";

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

    @Override
    public ToDo findById(Long id) throws ToDoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Override
    public ToDo completeToDo(Long id, CompleteToDoRequest request)
            throws ToDoNotFoundException, AlreadyCompletedException, InvalidWorkingTimeException {

        ToDo toDo = findById(id);

        if (toDo.isCompleted()) throw new AlreadyCompletedException(ALREADY_COMPLETED_MESSAGE);

        int actualWorkedDays = request.getActualWorkedTime();

        if (actualWorkedDays < 0) throw new InvalidWorkingTimeException(NEGATIVE_WORK_TIME_MESSAGE);

        LocalDate now = LocalDate.now();
        Period maxWorkedPeriod = Period.between(toDo.getCreatedAt(), now);

        int maxWorkedDays = maxWorkedPeriod.getDays();

        if (actualWorkedDays > maxWorkedDays) throw new InvalidWorkingTimeException(EXCEDED_MAX_WORK_TIME_MESSAGE);

        toDo.setCompleted(true);
        toDo.setActualWorkedTime(actualWorkedDays);
        toDo.setFinishedAt(now);

        return repository.save(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws ToDoNotFoundException, InvalidTaskException {
        ToDo toDo = findById(id);

        if (toDo.isCompleted()) throw new InvalidTaskException(CANT_DELETE_COMPLETED_MESSAGE);

        repository.delete(toDo);
    }
}

