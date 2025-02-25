package com.enotes.app.entity;


import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@MappedSuperclass
public class BaseModel {
	
	private boolean isActive;
	private boolean isDeleted;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	
}
