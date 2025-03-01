package com.enotes.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.app.dto.NotesDto;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.enotes.app.repo.ICategoryRepo;
import com.enotes.app.service.INoteService;
import com.enotes.app.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	@Autowired
	private INoteService noteService;
	
	@Autowired
	private ICategoryRepo categoryRepo;

	@PostMapping("/save-notes")
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws ResourceNotFoundException{
		categoryRepo.findById(notesDto.getCategory().getId())
		.orElseThrow(()->new ResourceNotFoundException("Invalid Category Id"));
		
		Boolean saveNotes = noteService.saveNotes(notesDto);
		return saveNotes
				? CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "Notes Saved Successfully")
				: CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Notes not saved");
	}
	
	@GetMapping("/getAllNotes")
	public ResponseEntity<?> getAllNotes(){
		List<NotesDto> allNotes = noteService.getAllNotes();
		return !CollectionUtils.isEmpty(allNotes)
				? CommonUtil.createBuildResponse(HttpStatus.OK, allNotes)
				: CommonUtil.createBuildResponse(HttpStatus.NOT_FOUND, ResponseEntity.noContent().build());
	}

}
