package com.Tarefas.ToDo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Tarefas.ToDo.DTO.TaskDTO;
import com.Tarefas.ToDo.Model.Priority;
import com.Tarefas.ToDo.Model.Status;
import com.Tarefas.ToDo.Service.TaskService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
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
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @ApiOperation("Encontra uma tarefa (GET)")
    @Operation(summary = "Retorna uma tarefa", description = "Retorna uma tarefa cadastrada por id")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<TaskDTO> taskDTO = taskService.getTaskById(id);
        return taskDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation("Cria uma tarefa (POST)")
    @Operation(summary = "Cria uma tarefa", description = "Cria uma tarefa nova")
    @PostMapping
    public ResponseEntity<List<TaskDTO>> createTasks(@RequestBody List<TaskDTO> taskDTOs) {
        List<TaskDTO> createdTasks = taskService.createTasks(taskDTOs);
        return ResponseEntity.ok(createdTasks);
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
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO updatedTaskDTO = taskService.updateTask(id, taskDTO);
            return ResponseEntity.ok(updatedTaskDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Atualiza parcialmente uma tarefa (PATCH)")
    @Operation(summary = "Atualiza parcialmente uma tarefa", description = "Atualiza parcialmente uma tarefa cadastrada por id")
    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> patchTask(
            @PathVariable Long id,
            @ApiParam(value = "Atualizar status da tarefa", allowableValues = "TODO, IN_PROGRESS, DONE") @RequestParam(required = false) Status status) {
        try {
            TaskDTO updatedTaskDTO = taskService.patchTask(id, status);
            return ResponseEntity.ok(updatedTaskDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Encontra tarefas por status (GET)")
    @Operation(summary = "Retorna tarefas por status", description = "Retorna todas as tarefas que possuem o status especificado")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Status status) {
        List<TaskDTO> taskDTOs = taskService.getTasksByStatus(status);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por prioridade (GET)")
    @Operation(summary = "Retorna tarefas por prioridade", description = "Retorna todas as tarefas que possuem a prioridade especificada")
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(@PathVariable Priority priority) {
        List<TaskDTO> taskDTOs = taskService.getTasksByPriority(priority);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por data de vencimento anterior a uma data específica (GET)")
    @Operation(summary = "Retorna tarefas por data de vencimento", description = "Retorna todas as tarefas cuja data de vencimento é anterior à data especificada")
    @GetMapping("/duedatebefore/{date}")
    public ResponseEntity<?> getTasksByDueDateBefore(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TaskDTO> taskDTOs = taskService.getTasksByDueDateBefore(date);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com a data de vencimento anterior a " + date);
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por data de vencimento posterior a uma data específica (GET)")
    @Operation(summary = "Retorna tarefas por data de vencimento posterior", description = "Retorna todas as tarefas cuja data de vencimento é posterior à data especificada")
    @GetMapping("/duedateafter/{date}")
    public ResponseEntity<?> getTasksByDueDateAfter(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TaskDTO> taskDTOs = taskService.getTasksByDueDateAfter(date);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com data de vencimento posterior a " + date);
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por data de vencimento entre duas datas específicas (GET)")
    @Operation(summary = "Retorna tarefas por data de vencimento entre duas datas", description = "Retorna todas as tarefas cuja data de vencimento está entre duas data especificadas")
    @GetMapping("/duedatebetween/{startDate}/{endDate}")
    public ResponseEntity<?> getTasksByDueDateBetween(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TaskDTO> taskDTOs = taskService.getTasksByDueDateBetween(startDate, endDate);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com data de vencimento entre " + startDate + " e " + endDate);
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por título contendo uma palavra-chave (GET)")
    @Operation(summary = "Retorna tarefas por título contendo uma palavra-chave", description = "Retorna todas tarefas por título contendo uma palavra-chave especificada")
    @GetMapping("/bytitle/{keyword}")
    public ResponseEntity<?> getTasksByTitleContaining(@PathVariable("keyword") String keyword) {
        List<TaskDTO> taskDTOs = taskService.getTasksByTitleContaining(keyword);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com título contendo '" + keyword + "'");
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por descrição contendo uma palavra-chave (GET)")
    @Operation(summary = "Retorna tarefas por descrição contendo uma palavra-chave", description = "Retorna todas tarefas por descrição contendo uma palavra-chave especificada")
    @GetMapping("/bydescription/{keyword}")
    public ResponseEntity<?> getTasksByDescriptionContaining(@PathVariable("keyword") String keyword) {
        List<TaskDTO> taskDTOs = taskService.getTasksByDescriptionContaining(keyword);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com descrição contendo '" + keyword + "'");
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por status e prioridade (GET)")
    @Operation(summary = "Retorna tarefas por status e prioridade", description = "Retorna todas as tarefas por status e prioridade especificadas")
    @GetMapping("/bystatuspriority")
    public ResponseEntity<?> getTasksByStatusAndPriority(
            @RequestParam("status") Status status,
            @RequestParam("priority") Priority priority) {
        List<TaskDTO> taskDTOs = taskService.getTasksByStatusAndPriority(status, priority);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com status '" + status + "' e prioridade '" + priority + "'");
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por status e data de vencimento antes de uma determinada data (GET)")
    @Operation(summary = "Retorna tarefas por status e data de vencimento antes de uma determinada data", description = "Retorna todas as tarefas por status e data de vencimento antes de uma determinada data especificada")
    @GetMapping("/bystatusduedatebefore")
    public ResponseEntity<?> getTasksByStatusAndDueDateBefore(
            @RequestParam("status") Status status,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TaskDTO> taskDTOs = taskService.getTasksByStatusAndDueDateBefore(status, date);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com status '" + status + "' e data de vencimento antes de '" + date + "'");
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por status e data de vencimento depois de uma determinada data (GET)")
    @Operation(summary = "Retorna tarefas por status e data de vencimento depois de uma determinada data", description = "Retorna todas as tarefas por status e data de vencimento depois de uma determinada data especificada")
    @GetMapping("/bystatusduedateafter")
    public ResponseEntity<?> getTasksByStatusAndDueDateAfter(
            @RequestParam("status") Status status,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TaskDTO> taskDTOs = taskService.getTasksByStatusAndDueDateAfter(status, date);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com status '" + status + "' e data de vencimento após '" + date + "'");
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Encontra tarefas por status e data de vencimento entre duas datas (GET)")
    @Operation(summary = "Retorna tarefas por status e data de vencimento entre duas datas", description = "Retorna todas as tarefas por status e data de vencimento entre duas datas especificadas")
    @GetMapping("/bystatusduedatebetween")
    public ResponseEntity<?> getTasksByStatusAndDueDateBetween(
            @RequestParam("status") Status status,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TaskDTO> taskDTOs = taskService.getTasksByStatusAndDueDateBetween(status, startDate, endDate);
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok("Nenhuma tarefa encontrada com status '" + status + "' e data de vencimento entre '" + startDate + "' e '" + endDate + "'");
        } else {
            return ResponseEntity.ok(taskDTOs);
        }
    }

    @ApiOperation("Conta tarefas por status (GET)")
    @Operation(summary = "Conta tarefas por status", description = "Retorna a conta de todas as tarefas por status especificado")
    @GetMapping("/countbystatus")
    public ResponseEntity<?> countTasksByStatus(@RequestParam("status") Status status) {
        long count = taskService.countTasksByStatus(status);
        return ResponseEntity.ok("Número de tarefas com status '" + status + "': " + count);
    }

    @ApiOperation("Conta tarefas por prioridade (GET)")
    @Operation(summary = "Conta tarefas por prioridade", description = "Retorna a conta de todas as tarefas por prioridade especificada")
    @GetMapping("/countbypriority")
    public ResponseEntity<?> countTasksByPriority(@RequestParam("priority") Priority priority) {
        long count = taskService.countTasksByPriority(priority);
        return ResponseEntity.ok("Número de tarefas com prioridade '" + priority + "': " + count);
    }

    @ApiOperation("Conta tarefas por data de vencimento antes de uma determinada data (GET)")
    @Operation(summary = "Conta tarefas por data de vencimento antes de uma determinada data", description = "Retorna a conta de todas as tarefas por data de vencimento antes de uma determinada data especificada")
    @GetMapping("/countduedatebefore")
    public ResponseEntity<?> countTasksByDueDateBefore(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        long count = taskService.countTasksByDueDateBefore(date);
        return ResponseEntity.ok("Número de tarefas com data de vencimento antes de '" + date + "': " + count);
    }

    @ApiOperation("Conta tarefas por data de vencimento depois de uma determinada data (GET)")
    @Operation(summary = "Conta tarefas por data de vencimento depois de uma determinada data", description = "Retorna a conta de todas as tarefas por data de vencimento depois de uma determinada data especificada")
    @GetMapping("/countduedateafter")
    public ResponseEntity<?> countTasksByDueDateAfter(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        long count = taskService.countTasksByDueDateAfter(date);
        return ResponseEntity.ok("Número de tarefas com data de vencimento depois de '" + date + "': " + count);
    }
}

