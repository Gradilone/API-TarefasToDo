package com.Tarefas.ToDo.Service;
import com.Tarefas.ToDo.Model.Task;
// import com.Tarefas.ToDo.Repository.TaskRepository;
import com.Tarefas.ToDo.Repository.TaskRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Tarefas.ToDo.Model.Status;
import com.Tarefas.ToDo.DTO.TaskDTO;
import com.Tarefas.ToDo.Mapper.TaskMapper;
import com.Tarefas.ToDo.Model.Priority;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO);
    }

    public List<TaskDTO> createTasks(List<TaskDTO> taskDTOs) {
        List<Task> tasks = taskDTOs.stream()
                .map(taskMapper::toEntity)
                .collect(Collectors.toList());
        return taskRepository.saveAll(tasks).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskDTO updateTask(Long id, TaskDTO updatedTaskDTO) {
        Task updatedTask = taskMapper.toEntity(updatedTaskDTO);
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());
            return taskMapper.toDTO(taskRepository.save(existingTask));
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public TaskDTO patchTask(Long id, Status status) {
        return taskRepository.findById(id).map(existingTask -> {
            if (status != null) {
                existingTask.setStatus(status);
            }
            return taskMapper.toDTO(taskRepository.save(existingTask));
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<TaskDTO> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByDueDateBefore(LocalDate date) {
        return taskRepository.findByDueDateBefore(date).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByDueDateAfter(LocalDate date) {
        return taskRepository.findByDueDateAfter(date).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByDueDateBetween(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByTitleContaining(String keyword) {
        return taskRepository.findByTitleContaining(keyword).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByDescriptionContaining(String keyword) {
        return taskRepository.findByDescriptionContaining(keyword).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByStatusAndPriority(Status status, Priority priority) {
        return taskRepository.findByStatusAndPriority(status, priority).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByStatusAndDueDateBefore(Status status, LocalDate date) {
        return taskRepository.findByStatusAndDueDateBefore(status, date).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByStatusAndDueDateAfter(Status status, LocalDate date) {
        return taskRepository.findByStatusAndDueDateAfter(status, date).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByStatusAndDueDateBetween(Status status, LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByStatusAndDueDateBetween(status, startDate, endDate).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public long countTasksByStatus(Status status) {
        return taskRepository.countByStatus(status);
    }

    public long countTasksByPriority(Priority priority) {
        return taskRepository.countByPriority(priority);
    }

    public long countTasksByDueDateBefore(LocalDate date) {
        return taskRepository.countByDueDateBefore(date);
    }

    public long countTasksByDueDateAfter(LocalDate date) {
        return taskRepository.countByDueDateAfter(date);
    }
}