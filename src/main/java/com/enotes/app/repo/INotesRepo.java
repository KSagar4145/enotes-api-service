package com.enotes.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enotes.app.dto.NotesDto;
import com.enotes.app.entity.Notes;

public interface INotesRepo extends JpaRepository<Notes, Integer> {

	List<NotesDto> findByCreatedBy(Integer userId);

	Page<Notes> findByCreatedBy(Integer userId, Pageable  pageable);//not used in Oracle Sql Devloper

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);
	


	

//
//	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);
//
//	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);

}
