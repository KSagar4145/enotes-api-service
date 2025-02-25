package com.enotes.app.service;

import java.util.List;

import com.enotes.app.entity.Category;

public interface ICategoryService {

	public Boolean saveCatagory(Category category);
	public List<Category> getAllCatagory();
	
}
