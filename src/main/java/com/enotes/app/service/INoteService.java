package com.enotes.app.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.app.dto.NotesDto;
import com.enotes.app.entity.FileDetails;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface INoteService {
	
//	public Boolean saveNotes(NotesDto notesDTo);
	public Boolean saveNotes(String notes, MultipartFile file) throws JsonMappingException, JsonProcessingException, ResourceNotFoundException, IOException;
	
	public List<NotesDto> getAllNotes();

	public FileDetails getFileDetails(Integer fileDetailsId) throws ResourceNotFoundException;

	public byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, FileNotFoundException, IOException;


	

}
