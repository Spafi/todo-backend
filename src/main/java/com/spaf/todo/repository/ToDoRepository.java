package com.spaf.todo.repository;

import com.spaf.todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Query("UPDATE todo SET completed = ?1")
    void updateStatus(boolean status);
}
