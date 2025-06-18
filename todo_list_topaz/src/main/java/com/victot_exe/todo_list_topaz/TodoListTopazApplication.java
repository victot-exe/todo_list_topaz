package com.victot_exe.todo_list_topaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListTopazApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListTopazApplication.class, args);
	}

}//TODO fazer uma migration para ver se o banco foi criado (pelo docker) e popular o mesmo.
