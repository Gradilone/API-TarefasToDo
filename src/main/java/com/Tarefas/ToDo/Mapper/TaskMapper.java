package com.Tarefas.ToDo.Mapper;

import org.springframework.stereotype.Component;

import com.Tarefas.ToDo.DTO.TaskDTO;
import com.Tarefas.ToDo.Model.Task;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());

        return dto;
    }

    public Task toEntity(TaskDTO dto) {
        if (dto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());

        return task;
    }
}
