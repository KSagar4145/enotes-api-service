package com.enotes.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.enotes.app.dto.NotesDto;
import com.enotes.app.entity.Notes;
import com.enotes.app.repo.INotesRepo;

@Service
@Transactional
public class NotesServiceImpl implements INoteService {
	
	@Autowired
	private INotesRepo notesRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveNotes(NotesDto notesDTo) {
		Notes notes = mapper.map(notesDTo, Notes.class);
		Notes saveNotes = notesRepo.save(notes);
		return !ObjectUtils.isEmpty(saveNotes)?true:false;
	}

	@Override
	public List<NotesDto> getAllNotes() {
		List<NotesDto> noteList = notesRepo.findAll().stream().map(note->mapper.map(note, NotesDto.class)).collect(Collectors.toList());
		return noteList;
	}

}
