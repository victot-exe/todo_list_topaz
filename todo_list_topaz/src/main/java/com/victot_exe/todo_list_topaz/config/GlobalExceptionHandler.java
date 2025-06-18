package com.victot_exe.todo_list_topaz.config;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.victot_exe.todo_list_topaz.exception.SemTarefasException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SemTarefasException.class)
    public ResponseEntity<String> handleSemTarefas(SemTarefasException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEnumInvalido(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidEx && invalidEx.getTargetType().isEnum()) {
            Class<?> enumType = invalidEx.getTargetType();
            String valorInvalido = invalidEx.getValue().toString();

            String valoresPermitidos = Arrays.stream(enumType.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            error.put("erro", "Valor inválido '" + valorInvalido + "' para o campo enum. Valores permitidos: " + valoresPermitidos);
        } else {
            error.put("erro", "Requisição mal formatada. Verifique os dados enviados.");
        }

        return ResponseEntity.badRequest().body(error);
    }


}
