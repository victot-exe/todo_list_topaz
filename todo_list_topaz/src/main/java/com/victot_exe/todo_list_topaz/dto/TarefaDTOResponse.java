package com.victot_exe.todo_list_topaz.dto;

import com.victot_exe.todo_list_topaz.model.Status;
import com.victot_exe.todo_list_topaz.model.Tarefa;

public record TarefaDTOResponse(
        Long id,
        String titulo,
        String descricao,
        Status status) {//TODO talvez usar string ao inves de status, ver qual é mais recomendado

    public static TarefaDTOResponse from(Tarefa t) {
        return new TarefaDTOResponse(t.getId(), t.getTitulo(), t.getDescricao(), t.getStatus());
    }
}
