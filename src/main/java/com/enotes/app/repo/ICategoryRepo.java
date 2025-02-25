package com.enotes.app.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.Category;

public interface ICategoryRepo extends JpaRepository<Category, Integer>{


}
