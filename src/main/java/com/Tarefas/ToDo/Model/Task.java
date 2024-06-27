package com.Tarefas.ToDo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "tb_tasks")
@Getter
@Setter
public class Task implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nm_titulo") 
    private String title;

    @Column(name="ds_descricao") 
    private String description;

    @Column(name="dt_tarefa") 
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Task() { }

}

