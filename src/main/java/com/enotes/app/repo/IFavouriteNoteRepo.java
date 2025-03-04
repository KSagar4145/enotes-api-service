package com.enotes.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.FavouriteNote;

public interface IFavouriteNoteRepo  extends JpaRepository<FavouriteNote, Integer> {

	List<FavouriteNote> findByUserId(int userId);

}
