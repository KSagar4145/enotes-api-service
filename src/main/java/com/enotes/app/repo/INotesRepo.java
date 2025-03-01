package com.enotes.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.Notes;

public interface INotesRepo extends JpaRepository<Notes, Integer> {

}
