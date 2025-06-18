package com.victot_exe.todo_list_topaz.repository;

import com.victot_exe.todo_list_topaz.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
