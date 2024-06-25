package com.Tarefas.ToDo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Tarefas.ToDo.Model.Status;
import com.Tarefas.ToDo.Service.TaskService;
import com.Tarefas.ToDo.Model.Task;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefa")
@Api(tags = "Tarefa Controller", description = "Operações relacionadas a tarefas")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ApiOperation("Encontra todas as tarefas (GET)")
    @Operation(summary = "Retorna todas as tarefas", description = "Retorna todas as tarefas cadastradas")
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @ApiOperation("Encontra uma tarefa (GET)")
    @Operation(summary = "Retorna uma tarefa", description = "Retorna uma tarefa cadastrada por id")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation("Cria uma tarefa (POST)")
    @Operation(summary = "Cria uma tarefa", description = "Cria uma tarefa nova")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @ApiOperation("Deleta uma tarefa (DELETE)")
    @Operation(summary = "Deleta uma tarefa", description = "Deleta uma tarefa cadastrada por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


    @ApiOperation("Atualiza totalmente uma tarefa (PUT)")
    @Operation(summary = "Atualiza totalmente uma tarefa", description = "Atualiza totalmente uma tarefa cadastrada por id")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Atualiza parcialmente uma tarefa (PATCH)")
    @Operation(summary = "Atualiza parcialmente uma tarefa", description = "Atualiza parcialmente uma tarefa cadastrada por id")
    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(
            @PathVariable Long id,
            @ApiParam(value = "Atualizar status da tarefa", allowableValues = "TODO, IN_PROGRESS, DONE") @RequestParam(required = false) Status status) {
        try {
            Task updatedTask = taskService.patchTask(id, status);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

