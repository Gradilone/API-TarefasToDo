package com.Tarefas.ToDo.Service;
import com.Tarefas.ToDo.Model.Task;
// import com.Tarefas.ToDo.Repository.TaskRepository;
import com.Tarefas.ToDo.Repository.TaskRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Tarefas.ToDo.Model.Status;
import com.Tarefas.ToDo.Model.Priority;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> createTasks(List<Task> tasks) {
        return taskRepository.saveAll(tasks);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());
            return taskRepository.save(existingTask);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task patchTask(Long id, Status status) {
        return taskRepository.findById(id).map(existingTask -> {
            if (status != null) {
                existingTask.setStatus(status);
            }
            return taskRepository.save(existingTask);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksByDueDateBefore(LocalDate date) {
        return taskRepository.findByDueDateBefore(date);
    }

    public List<Task> getTasksByDueDateAfter(LocalDate date) {
        return taskRepository.findByDueDateAfter(date);
    }

    public List<Task> getTasksByDueDateBetween(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

    public List<Task> getTasksByTitleContaining(String keyword) {
        return taskRepository.findByTitleContaining(keyword);
    }

    public List<Task> getTasksByDescriptionContaining(String keyword) {
        return taskRepository.findByDescriptionContaining(keyword);
    }

    public List<Task> getTasksByStatusAndPriority(Status status, Priority priority) {
        return taskRepository.findByStatusAndPriority(status, priority);
    }

    public List<Task> getTasksByStatusAndDueDateBefore(Status status, LocalDate date) {
        return taskRepository.findByStatusAndDueDateBefore(status, date);
    }

    public List<Task> getTasksByStatusAndDueDateAfter(Status status, LocalDate date) {
        return taskRepository.findByStatusAndDueDateAfter(status, date);
    }

    public List<Task> getTasksByStatusAndDueDateBetween(Status status, LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByStatusAndDueDateBetween(status, startDate, endDate);
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