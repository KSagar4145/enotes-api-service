package com.enotes.app.service;

import java.util.List;

import com.enotes.app.dto.TodoDto;



public interface ITodoService {

	public Boolean saveTodo(TodoDto todo) throws Exception;

	public TodoDto getTodoById(Integer id) throws Exception;

	public List<TodoDto> getTodoByUser();

}
