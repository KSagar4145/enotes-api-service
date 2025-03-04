package com.enotes.app.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.app.dto.FavouriteNoteDto;
import com.enotes.app.dto.NotesDto;
import com.enotes.app.dto.NotesResponse;
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

	public void softDeleteNotes(Integer notesId) throws ResourceNotFoundException;

	public NotesResponse getAllNotesByUser(Integer userId);//not used in Oracle Sql Devloper

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes(Integer userId);

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin(int userId);

	public void favoriteNotes(Integer noteId) throws Exception;

	public void unFavoriteNotes(Integer noteId) throws Exception;

	public List<FavouriteNoteDto> getUserFavoriteNotes() throws Exception;

	public Boolean copyNotes(Integer id) throws Exception;
	

}
