package com.enotes.app.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Todo extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
	@SequenceGenerator(name="todo_seq", sequenceName = "todo_seq", allocationSize = 1)
	private Integer id;

	private String title;

	@Column(name = "status")
	private Integer statusId;

}
