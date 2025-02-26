package com.enotes.app.repo;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.Category;

public interface ICategoryRepo extends JpaRepository<Category, Integer>{

	public List<Category> findByIsActiveTrue();

	public Optional<Category> findByIdAndIsDeletedFalse(Integer id);

	public List<Category> findByIsDeletedFalse();

}
