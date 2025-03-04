package com.enotes.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FavouriteNote {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "fav_note_seq")
	@SequenceGenerator(name="fav_note_seq", sequenceName = "fav_note_seq", allocationSize = 1)
	private Integer id;

	@ManyToOne
	private Notes note;

	private Integer userId;

}
