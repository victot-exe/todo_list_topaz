package com.victot_exe.todo_list_topaz.exception;

public class SemTarefasException extends RuntimeException {
    public SemTarefasException(String message){
        super(message);
    }

    public SemTarefasException(Long id){
        super("A tarefa com o id: {" + id + "} não foi encontrada.");
    }

    public SemTarefasException(){
        super();
    }
}
