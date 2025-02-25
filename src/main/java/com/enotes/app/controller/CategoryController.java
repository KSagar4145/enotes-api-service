package com.enotes.app.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.app.entity.Category;
import com.enotes.app.service.ICategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody Category category) {
		System.err.println("saveCategory Controller");
		Boolean savedCategoryFlg = categoryService.saveCatagory(category);
		return savedCategoryFlg ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category Not Saved") 
				: ResponseEntity.status(HttpStatus.CREATED).body("Category Saved");
	}
	
	
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		List<Category> categoryList = categoryService.getAllCatagory();
		return CollectionUtils.isEmpty(categoryList) ? ResponseEntity.noContent().build()
				: ResponseEntity.status(HttpStatus.OK).body(categoryList);
	}

}
