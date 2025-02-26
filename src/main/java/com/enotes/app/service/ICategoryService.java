package com.enotes.app.service;

import java.util.List;
import java.util.Optional;

import com.enotes.app.dto.CategoryDto;
import com.enotes.app.entity.Category;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;

public interface ICategoryService {

	public Boolean saveCatagory(Category category);
	public List<Category> getAllCatagory();
	
	public List<Category> findByIsActiveTrue();
//	public CategoryDto getCategoeryById(Integer id);
	public CategoryDto getCategoeryById(Integer id) throws ResourceNotFoundException;
	public Boolean deleteCategoeryById(Integer id);
	
}
