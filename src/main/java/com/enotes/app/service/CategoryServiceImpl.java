package com.enotes.app.service;

import java.lang.foreign.Linker.Option;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.app.entity.Category;
import com.enotes.app.repo.ICategoryRepo;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private ICategoryRepo categoryRepo;
	
	@Override
	public Boolean saveCatagory(Category category) {
		category.setActive(true);
		category.setDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		category.setUpdatedBy(null);
		category.setUpdatedOn(null);
		Category savedCategory = categoryRepo.save(category);
		return ObjectUtils.isEmpty(savedCategory);		
	}

	@Override
	public List<Category> getAllCatagory() {
		return categoryRepo.findAll();
	}

}


//private boolean isActive;
//private boolean isDeleted;
//private Integer createdBy;
//private Date createdOn;
//private Integer updatedBy;
//private Date updatedOn;
