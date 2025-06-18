package com.victot_exe.todo_list_topaz.dto;

import com.victot_exe.todo_list_topaz.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaDTORequest(
        @NotBlank(message = "O título é obrigatório") String titulo,
        @NotBlank(message = "A descrição é obrigatória") String descricao,
        @NotNull(message = "O status é obrigatório") Status status
) {
}
