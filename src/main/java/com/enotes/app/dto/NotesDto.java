package com.enotes.app.dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.enotes.app.entity.Category;
import com.enotes.app.entity.Notes;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

	private Integer id;

	private String title;

	private String description;

	private CategoryDto category;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
  	private Date updatedOn;
  	
  	private FilesDto fileDetails;
  	
  	@Data
  	@AllArgsConstructor
  	@NoArgsConstructor
  	public static class CategoryDto{
  		private Integer id;
  		private String name;
  	}
  	
  	
  	@Data
  	@AllArgsConstructor
  	@NoArgsConstructor
  	public static class FilesDto{
  		private Integer id;
  		private String originalFileName;
  		private String displayFileName;
  	}

}
