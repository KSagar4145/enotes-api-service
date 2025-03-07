package com.enotes.app.service;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.app.dto.TodoDto;
import com.enotes.app.dto.TodoDto.StatusDto;
import com.enotes.app.entity.Todo;
import com.enotes.app.entity.enums.TodoStatus;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.enotes.app.repo.ITodoRepo;



@Service
public class TodoServiceImpl implements ITodoService {

	@Autowired
	private ITodoRepo todoRepo;

	@Autowired
	private ModelMapper mapper;

//	@Autowired
//	private Validation validation;

	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		// validate todo status
		//validation.todoValidation(todoDto);

		Todo todo = mapper.map(todoDto, Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		Todo saveTodo = todoRepo.save(todo);
		if (!ObjectUtils.isEmpty(saveTodo)) {
			return true;
		}
		return false;
	}

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		Todo todo = todoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo Not Found ! id invalid"));
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		setStatus(todoDto,todo);
		return todoDto;
	}

	private void setStatus(TodoDto todoDto, Todo todo) {
		
		for(TodoStatus st:TodoStatus.values())
		{
			if(st.getId().equals(todo.getStatusId()))
			{
				StatusDto statusDto=StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				todoDto.setStatus(statusDto);
			}
		}
		
	}

	@Override
	public List<TodoDto> getTodoByUser() {
		Integer userId = 2;
		List<Todo> todos = todoRepo.findByCreatedBy(userId);
		return todos.stream().map(td -> mapper.map(td, TodoDto.class)).toList();
	}

}
