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
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PutMapping()
    public ResponseEntity<TarefaDTOResponse> createTarefa(@RequestBody @Valid TarefaDTORequest tarefaRequest){
        TarefaDTOResponse response = tarefaService.createTarefa(tarefaRequest);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTOResponse>> getAllTarefas(){
        List<TarefaDTOResponse> tarefas = tarefaService.getAllTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaDTOResponse> getTarefaById(@PathVariable Long id) {
        TarefaDTOResponse response = tarefaService.getTarefaById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<TarefaDTOResponse> updateTarefa(@PathVariable Long id, @RequestBody @Valid TarefaDTORequest tarefaRequest){
        TarefaDTOResponse response = tarefaService.updateTarefa(id, tarefaRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TarefaDTOResponse> deleteTarefa(@PathVariable Long id){
        tarefaService.deleteTarefaById(id);
        return ResponseEntity.noContent().build();
    }

}
