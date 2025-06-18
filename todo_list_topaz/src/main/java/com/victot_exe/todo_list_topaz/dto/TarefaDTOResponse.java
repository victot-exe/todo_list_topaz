package com.victot_exe.todo_list_topaz.dto;

import com.victot_exe.todo_list_topaz.model.Status;

public record TarefaDTOResponse(
        Long id,
        String titulo,
        String descricao,
        Status status) {//TODO talvez usar string ao inves de status, ver qual é mais recomendado
}
