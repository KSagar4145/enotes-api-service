package com.enotes.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.app.dto.CategoryDto;
import com.enotes.app.entity.Category;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.enotes.app.repo.ICategoryRepo;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private ICategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Boolean saveCatagory(Category category) {
		
		if(ObjectUtils.isEmpty(category.getId())) {
			//category.setActive(true);
			category.setDeleted(false);
			category.setCreatedBy(1);
			category.setCreatedOn(new Date());
			category.setUpdatedBy(null);
			category.setUpdatedOn(null);
		}else {
			updateCategory(category);
		}
		
		
		
		Category savedCategory = categoryRepo.save(category);
		return ObjectUtils.isEmpty(savedCategory);		
	}

	private void updateCategory(Category category) {
		Optional<Category> existingCatOpt = categoryRepo.findById(category.getId());
		existingCatOpt.ifPresent((existingCat)->{
		category.setCreatedBy(existingCat.getCreatedBy());
		category.setCreatedOn(existingCat.getCreatedOn());
		category.setUpdatedBy(1);
		category.setUpdatedOn(new Date());
		});
		
	}

	@Override
	public List<Category> getAllCatagory() {
		//return categoryRepo.findAll();
		return categoryRepo.findByIsDeletedFalse();
	}

	@Override
	public List<Category> findByIsActiveTrue() {
		return categoryRepo.findByIsActiveTrue();
	}

	
//	@Override
//	public CategoryDto getCategoeryById(Integer id) {
//		Optional<Category> catById = categoryRepo.findByIdAndIsDeletedFalse(id);
//		return catById.map(cat -> modelMapper.map(cat, CategoryDto.class)).orElse(null);
//	}
	
	@Override
	public CategoryDto getCategoeryById(Integer id) throws ResourceNotFoundException {
			Category catById = categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id:"+id));
		
		return  modelMapper.map(catById, CategoryDto.class);
		
		
	}

	@Override
	public Boolean deleteCategoeryById(Integer id) {
		Optional<Category> catById = categoryRepo.findById(id);
		return catById.map(cat -> {
			cat.setDeleted(true);
			categoryRepo.save(cat);
			return true;
			}).orElse(false);
		
	}
	
	

}

