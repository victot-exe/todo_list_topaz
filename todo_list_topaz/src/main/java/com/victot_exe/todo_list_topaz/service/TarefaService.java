package com.victot_exe.todo_list_topaz.service;

import com.victot_exe.todo_list_topaz.dto.TarefaDTORequest;
import com.victot_exe.todo_list_topaz.dto.TarefaDTOResponse;
import com.victot_exe.todo_list_topaz.exception.SemTarefasException;
import com.victot_exe.todo_list_topaz.model.Tarefa;
import com.victot_exe.todo_list_topaz.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public TarefaDTOResponse createTarefa(TarefaDTORequest tarefaRequest){

        Tarefa tarefa = new Tarefa(
                tarefaRequest.titulo(),
                tarefaRequest.descricao(),
                tarefaRequest.status()
        );

        tarefaRepository.save(tarefa);
        return new TarefaDTOResponse(
                                    tarefa.getId(),
                                    tarefa.getTitulo(),
                                    tarefa.getDescricao(),
                                    tarefa.getStatus());
    }

    public List<TarefaDTOResponse> getAllTarefas(){

        List<Tarefa> tarefas = tarefaRepository.findAll();

        if(tarefas.isEmpty())
            throw new SemTarefasException("Ainda não foram cadastradas tarefas no banco de dados");


        return tarefas.stream().map(
                t -> new TarefaDTOResponse(
                        t.getId(),
                        t.getTitulo(),
                        t.getDescricao(),
                        t.getStatus())).toList();
    }

    public TarefaDTOResponse getTarefaById(Long id){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);

        if(tarefa.isEmpty())
            throw new SemTarefasException(id);


        return TarefaDTOResponse.from(tarefa.get());
    }

    public TarefaDTOResponse updateTarefa(Long id, TarefaDTORequest tarefaRequest){

        Tarefa tarefaOld = tarefaRepository.findById(id).orElseThrow(() -> new SemTarefasException(id));

        tarefaOld.setTitulo(tarefaRequest.titulo());
        tarefaOld.setDescricao(tarefaRequest.descricao());
        tarefaOld.setStatus(tarefaRequest.status());

        return TarefaDTOResponse.from(
                tarefaRepository.save(tarefaOld));
    }

    public void deleteTarefaById(Long id){

        if(!tarefaRepository.existsById(id))
            throw new SemTarefasException(id);

        tarefaRepository.deleteById(id);
    }
}
