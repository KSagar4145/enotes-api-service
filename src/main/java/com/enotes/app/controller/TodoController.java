package com.enotes.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.app.dto.TodoDto;
import com.enotes.app.service.ITodoService;
import com.enotes.app.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

	@Autowired
	private ITodoService todoService;

	@PostMapping("/addTodo")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception {
		Boolean saveTodo = todoService.saveTodo(todo);
		if (saveTodo) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"Todo Saved Success");
		} else {
			return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Todo not save");
		}
	}

	@GetMapping("/getTodoById/{id}")
	public ResponseEntity<?> saveTodo(@PathVariable Integer id) throws Exception {
		TodoDto todo = todoService.getTodoById(id);
		return CommonUtil.createBuildResponse(HttpStatus.OK, todo);
	}

	@GetMapping("/todoList")
	public ResponseEntity<?> getAllTodoByUser() throws Exception {
		List<TodoDto> todoList = todoService.getTodoByUser();
		if (CollectionUtils.isEmpty(todoList)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(HttpStatus.OK, todoList);
	}

}
