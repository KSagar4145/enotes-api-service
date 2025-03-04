package com.enotes.app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.app.dto.FavouriteNoteDto;
import com.enotes.app.dto.NotesDto;
import com.enotes.app.dto.NotesResponse;
import com.enotes.app.entity.FileDetails;
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

//	@PostMapping("/save-notes")
//	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws ResourceNotFoundException{
//		Boolean saveNotes = noteService.saveNotes(notesDto);
//		return saveNotes
//				? CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "Notes Saved Successfully")
//				: CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Notes not saved");
//	}
	
	//used form-data from postman
	@PostMapping("/save-notes")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, 
			@RequestParam(required=false) MultipartFile file) throws ResourceNotFoundException, IOException{
		Boolean saveNotes = noteService.saveNotes(notes,file);
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
	
	
	@GetMapping("/download-file/{fileDetailsId}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer fileDetailsId ) throws FileNotFoundException, ResourceNotFoundException, IOException{
		System.out.println("fileDetailsId: "+fileDetailsId);
		FileDetails fileDetails =noteService.getFileDetails(fileDetailsId);
		
		byte[] data = noteService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachement", fileDetails.getOriginalFileName());
		
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
	} 
	
	//not used in Oracle Sql Devloper	
//	@GetMapping("/user-notes")
//	public ResponseEntity<?> getAllNotesByUser() {
//	    Integer userId = 2;
//	    NotesResponse notes = noteService.getAllNotesByUser(userId);
////	    if (CollectionUtils.isEmpty(notes)) {
////	        return ResponseEntity.noContent().build();
////	    }
////	    return CommonUtil.createBuildResponse(HttpStatus.OK,notes);
//	    
//	    return CommonUtil.createBuildResponse(HttpStatus.OK,notes);
//	}

	
	@DeleteMapping("/delete-notes/{notesId}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer notesId) throws ResourceNotFoundException{
		noteService.softDeleteNotes(notesId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "Notes Deleted Successfully");
	}
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
		noteService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage( HttpStatus.OK,"Notes restore Success");
	}
	
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
		Integer userId = 2;
		List<NotesDto> notes = noteService.getUserRecycleBinNotes(userId);
		if (CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"Notes not avaible in Recycle Bin");
		}
		return CommonUtil.createBuildResponse(HttpStatus.OK, notes);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
		noteService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "Delete Success");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecyleBin() throws Exception {
		int userId = 2;
		noteService.emptyRecycleBin(userId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "Delete Success");
	}

	@PostMapping("/fav/{noteId}")
	public ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws Exception {
		noteService.favoriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "Notes added Favorite");
	}

	@DeleteMapping("/un-fav/{favNotId}")
	public ResponseEntity<?> unFavoriteNote(@PathVariable Integer favNotId) throws Exception {
		noteService.unFavoriteNotes(favNotId);
		return CommonUtil.createBuildResponseMessage( HttpStatus.OK, "Remove Favorite");
	}

	@GetMapping("/fav-note")
	public ResponseEntity<?> getUserfavoriteNote() throws Exception {

		List<FavouriteNoteDto> userFavoriteNotes = noteService.getUserFavoriteNotes();
		if (CollectionUtils.isEmpty(userFavoriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(HttpStatus.OK, userFavoriteNotes);
	}

	@GetMapping("/copy/{id}")
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {
		Boolean copyNotes = noteService.copyNotes(id);
		if (copyNotes) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "Copied success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Copy failed ! Try Again");
	}
	
	

}
