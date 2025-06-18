package com.victot_exe.todo_list_topaz.controller;


import com.victot_exe.todo_list_topaz.dto.TarefaDTORequest;
import com.victot_exe.todo_list_topaz.dto.TarefaDTOResponse;
import com.victot_exe.todo_list_topaz.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tarefas")
public class TarefasController {

    @Autowired
    private TarefaService tarefaService;

    @PutMapping()
    public ResponseEntity<TarefaDTOResponse> createTarefa(@RequestBody @Valid TarefaDTORequest tarefaRequest){
        //TODO implementar
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTOResponse>> getAllTarefas(){
        //TODO implementar
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaDTOResponse> getTarefaById(@RequestParam Long id) {
        //TODO implementar
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TarefaDTOResponse> updateTarefa(@RequestParam Long id, @RequestBody @Valid TarefaDTORequest tarefaRequest){
        //TODO implementar
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TarefaDTOResponse> deleteTarefa(@RequestParam Long id){
        //TODO implementar
        return ResponseEntity.noContent().build();
    }
}
