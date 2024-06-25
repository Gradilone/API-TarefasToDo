package com.Tarefas.ToDo.Service;
import com.Tarefas.ToDo.Model.Task;
// import com.Tarefas.ToDo.Repository.TaskRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import com.Tarefas.ToDo.Model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

   private List<Task> tasks;
    private AtomicLong idCounter;

    @PostConstruct
    public void init() {
        tasks = new ArrayList<>();
        idCounter = new AtomicLong();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> getTaskById(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    public Task createTask(Task task) {
        task.setId(idCounter.incrementAndGet());
        tasks.add(task);
        return task;
    }

    public void deleteTask(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public Task updateTask(Long id, Task updatedTask) {
        return getTaskById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());
            return existingTask;
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task patchTask(Long id, Status status) {
        return getTaskById(id).map(existingTask -> {
            if (status != null) {
                existingTask.setStatus(status);
            }
            return existingTask;
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }
}