package com.Tarefas.ToDo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Tarefas.ToDo.Model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
