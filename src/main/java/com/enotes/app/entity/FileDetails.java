package com.enotes.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class FileDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
	@SequenceGenerator(name="file_seq", sequenceName = "file_seq", allocationSize = 1)
	private Integer id;
	private String uploadFileName;
	private String originalFileName;
	private String displayFileName;
	private String path;
	private Long fileSize;
	
	
	
	
	
	
	

}
