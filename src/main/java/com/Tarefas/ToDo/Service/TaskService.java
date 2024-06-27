package com.Tarefas.ToDo.Service;
import com.Tarefas.ToDo.Model.Task;
// import com.Tarefas.ToDo.Repository.TaskRepository;
import com.Tarefas.ToDo.Repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Tarefas.ToDo.Model.Status;

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

    public Task createTask(Task task) {
        return taskRepository.save(task);
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
}