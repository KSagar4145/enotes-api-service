package com.enotes.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.Todo;

public interface ITodoRepo extends JpaRepository<Todo, Integer> {

	List<Todo> findByCreatedBy(Integer userId);

}
