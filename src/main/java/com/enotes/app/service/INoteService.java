package com.enotes.app.service;

import java.util.List;

import com.enotes.app.dto.NotesDto;

public interface INoteService {
	public Boolean saveNotes(NotesDto notesDTo);
	
	public List<NotesDto> getAllNotes();

}
