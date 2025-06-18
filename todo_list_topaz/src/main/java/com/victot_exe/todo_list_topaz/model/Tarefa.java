package com.victot_exe.todo_list_topaz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @Getter @Setter
public class Tarefa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private Status status;

    public Tarefa(String titulo, String descricao, Status status){
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }
}
