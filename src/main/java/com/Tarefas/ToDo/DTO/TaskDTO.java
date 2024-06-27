package com.Tarefas.ToDo.DTO;

import java.time.LocalDate;

import com.Tarefas.ToDo.Model.Status;
import com.Tarefas.ToDo.Model.Priority;

import lombok.*;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private Status status;

    private Priority priority;
}
