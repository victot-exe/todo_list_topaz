package com.victot_exe.todo_list_topaz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @Getter @Setter
public class Tarefa {

    @Id
    private Long id;
    private String titulo;
    private String descricao;
    private Status staus;
}
