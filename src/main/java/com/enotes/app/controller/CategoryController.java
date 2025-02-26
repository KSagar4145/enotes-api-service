package com.enotes.app.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.app.dto.CategoryDto;
import com.enotes.app.entity.Category;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.enotes.app.service.ICategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody Category category) {
		System.err.println("saveCategory Controller");
		Boolean savedCategoryFlg = categoryService.saveCatagory(category);
		return savedCategoryFlg ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category Not Saved") 
				: ResponseEntity.status(HttpStatus.CREATED).body("Category Saved");
	}
	
	
	
	@GetMapping("/all-category")
	public ResponseEntity<?> getAllCategory(){
		List<Category> categoryList = categoryService.getAllCatagory();
		return CollectionUtils.isEmpty(categoryList) ? ResponseEntity.noContent().build()
				: ResponseEntity.status(HttpStatus.OK).body(categoryList);
	}
	
	
	
	@GetMapping("/active-categories")
    public ResponseEntity<?> getActiveCategories() {
        List<Category> activeCategories = categoryService.findByIsActiveTrue();
        
        
//        // Converting List<Category> to List<CategoryDto>
//        List<CategoryDto> dtoList = activeCategories.stream()
//            .map(category -> new CategoryDto(
//                category.getId(), 
//                category.getName(), 
//                category.getDescription(), 
//                category.isActive()
//            ))
//            .collect(Collectors.toList());
        
//        // Converting List<Category> to List<CategoryDto>
//        List<CategoryDto> dtoList = activeCategories.stream()
//            .map(category -> new CategoryDto(
//                category.getId(), 
//                category.getName(), 
//                category.getDescription(), 
//                category.isActive()
//            )).toList();
        
     
		// Converting List<Category> to List<CategoryDto> using ModelMapper
        List<CategoryDto> dtoList = activeCategories.stream()
            .map(category -> modelMapper.map(category, CategoryDto.class))
            .collect(Collectors.toList());
        
        
        return ResponseEntity.ok(dtoList);
    }
	
	
	@GetMapping("getCategoryById/{id}")
	//public ResponseEntity<?> getCategoeryById(@PathVariable Integer id)
	//If I have used the try catch insted of the throws the GlobalExceptionHandler will not get called
	public ResponseEntity<?> getCategoeryById(@PathVariable Integer id) throws ResourceNotFoundException{
		CategoryDto catDto = categoryService.getCategoeryById(id);
		return ObjectUtils.isEmpty(catDto) 
				? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Category found for given Id")
				: ResponseEntity.status(HttpStatus.OK).body(catDto);
	}
	
	
	@DeleteMapping("deleteCategoryById/{id}")
	public ResponseEntity<?> deleteCategoeryById(@PathVariable Integer id){
		Boolean catDto = categoryService.deleteCategoeryById(id);
		return catDto
				? ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully ")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Category found for given Id");
				
		
	}


}
