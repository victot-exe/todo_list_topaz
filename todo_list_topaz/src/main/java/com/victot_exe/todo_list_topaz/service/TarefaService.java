package com.victot_exe.todo_list_topaz.service;

import com.victot_exe.todo_list_topaz.dto.TarefaDTORequest;
import com.victot_exe.todo_list_topaz.model.Status;
import com.victot_exe.todo_list_topaz.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public void createTarefa(@Valid TarefaDTORequest tarefaRequest){
        //TODO implementar
    }

    public void getAllTarefas(){
        //TODO implmentar
    }

    public void getTarefaById(Long id){
        //TODO implementar
    }

    public void getTarefasComStatus(Status status){//Talvez eu não use esse
        //TODO implementar
    }

    public void updateTarefa(Long id, @Valid TarefaDTORequest tarefaRequest){
        //TODO implementar
    }

    public void deleteTarefaById(Long id){
        //TODO implementar
    }
}
