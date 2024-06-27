package com.Tarefas.ToDo.Repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tarefas.ToDo.Model.Priority;
import com.Tarefas.ToDo.Model.Status;
import com.Tarefas.ToDo.Model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);

    List<Task> findByPriority(Priority priority);

    List<Task> findByDueDateBefore(LocalDate date);

    List<Task> findByDueDateAfter(LocalDate date);

    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    List<Task> findByTitleContaining(String keyword);

    List<Task> findByDescriptionContaining(String keyword);

    List<Task> findByStatusAndPriority(Status status, Priority priority);

    List<Task> findByStatusAndDueDateBefore(Status status, LocalDate date);

    List<Task> findByStatusAndDueDateAfter(Status status, LocalDate date);

    List<Task> findByStatusAndDueDateBetween(Status status, LocalDate startDate, LocalDate endDate);

    long countByStatus(Status status);

    long countByPriority(Priority priority);

    long countByDueDateBefore(LocalDate date);

    long countByDueDateAfter(LocalDate date);

}
