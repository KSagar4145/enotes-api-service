package com.enotes.app.entity;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notes extends BaseModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notes_seq")
	@SequenceGenerator(name="notes_seq", sequenceName = "notes_seq", allocationSize = 1)
	private Integer id;
	
	private String title;
	
	private String description;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private FileDetails fileDetails;
	
	private Boolean isDeleted;
	
	private Date deletedOn;
	
	
	

}
