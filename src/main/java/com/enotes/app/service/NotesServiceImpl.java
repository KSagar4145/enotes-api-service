package com.enotes.app.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.app.dto.FavouriteNoteDto;
import com.enotes.app.dto.NotesDto;
import com.enotes.app.dto.NotesDto.FilesDto;
import com.enotes.app.dto.NotesResponse;
import com.enotes.app.entity.Category;
import com.enotes.app.entity.FavouriteNote;
import com.enotes.app.entity.FileDetails;
import com.enotes.app.entity.Notes;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.enotes.app.repo.ICategoryRepo;
import com.enotes.app.repo.IFavouriteNoteRepo;
import com.enotes.app.repo.IFileRepo;
import com.enotes.app.repo.INotesRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class NotesServiceImpl implements INoteService {
	
	@Autowired
	private INotesRepo notesRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ICategoryRepo categoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private IFileRepo fileRepo;

	@Autowired
	private IFavouriteNoteRepo favouriteNoteRepo;

//	@Override
//	public Boolean saveNotes(NotesDto notesDTo) {
//	categoryRepo.findById(notesDto.getCategory().getId())
//	.orElseThrow(()->new ResourceNotFoundException("Invalid Category Id"));
//		Notes notes = mapper.map(notesDTo, Notes.class);
//		Notes saveNotes = notesRepo.save(notes);
//		return !ObjectUtils.isEmpty(saveNotes)?true:false;
//	}
	
	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws ResourceNotFoundException, IOException {
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
		
		if(!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNotes(notesDto,file);
		}
		
		//check category exists
		categoryRepo.findById(notesDto.getCategory().getId())
		.orElseThrow(()->new ResourceNotFoundException("Invalid Category Id"));
		
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDtls = saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		}else {
			
			if(!ObjectUtils.isEmpty(notesDto.getId())) {
				notesMap.setFileDetails(null);
			}
		}
		notesMap.setIsDeleted(false);
		Notes saveNotes = notesRepo.save(notesMap);
		
		return !ObjectUtils.isEmpty(saveNotes)?true:false;
		
	}
	

	private void updateNotes(NotesDto notesDto, MultipartFile file) throws ResourceNotFoundException {
		
		Notes existNotes = notesRepo.findById(notesDto.getId())
		.orElseThrow(()->new ResourceNotFoundException("not found Invalid Notes Id "));
		
		if (ObjectUtils.isEmpty(file)) {
		    notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FilesDto.class));
		}

		
		
	
}


	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		if( !ObjectUtils.isEmpty(file) &&  !file.isEmpty()) {
			
			String originalFilename = file.getOriginalFilename() ;
			String extension = FilenameUtils.getExtension (originalFilename) ;
			System.out.println(">>>>>>>>>>extension: "+extension+" >>>>>>>>>originalFilename: "+originalFilename);
			List<String> extensionAllow =Arrays.asList("pdf","xlsx", "xml","jpg", "png");
			if (!extensionAllow. contains(extension)) {
				throw new IllegalArgumentException ("invalid file format ! Upload only .pdf, .xlsx, .xml, .jpg, .png");
			}
		
			String rndmString = UUID.randomUUID().toString();
			String uploadFileName = rndmString+"."+extension;
			
			File saveFile = new File(uploadPath);
			
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			
			String storePath = uploadPath.concat(uploadFileName);
			
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			
			if(upload!=0) {
				FileDetails fileDtls=new FileDetails() ;
				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayFileName(originalFilename));
				fileDtls.setUploadFileName(uploadFileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				
				FileDetails saveFileDtls = fileRepo.save(fileDtls);
				return saveFileDtls;
			}
		}
		
	return null;
}

	private String getDisplayFileName(String originalFilename) {
		//java_programming_tutorial.pdf
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		if(fileName.length()>8) {
			 fileName = fileName.substring(0,7);
		}
		fileName = fileName+"."+extension;
		return fileName;
	}


	@Override
	public List<NotesDto> getAllNotes() {
		List<NotesDto> noteList = notesRepo.findAll().stream().map(note->mapper.map(note, NotesDto.class)).collect(Collectors.toList());
		return noteList;
	}
	
	
	@Override
	public FileDetails getFileDetails(Integer fileDetailsId) throws ResourceNotFoundException {
		FileDetails fileDetails = fileRepo.findById(fileDetailsId).orElseThrow(()->new ResourceNotFoundException("Invalid File Details Id, File is not available"));
		return fileDetails;
	}


	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, IOException {
		
		InputStream io = new FileInputStream(fileDetails.getPath());
		
		return StreamUtils.copyToByteArray(io);
		
	}


	@Override
	public void softDeleteNotes(Integer notesId) throws ResourceNotFoundException {
		Notes notes = notesRepo.findById(notesId)
				.orElseThrow(()->new ResourceNotFoundException("Invalid Notes Id "));
		
		notes.setIsDeleted(true);
		notes.setDeletedOn(new Date());
		notesRepo.save(notes);
		
	}

	//not used in Oracle Sql Devloper
	@Override
	public NotesResponse getAllNotesByUser(Integer userId) {
		Pageable pageable = PageRequest.of(0, 5);
		Page<Notes> pageNotes = notesRepo.findByCreatedBy(userId, pageable);

		List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

		NotesResponse notesResponse = NotesResponse.builder()
		    .notes(notesDto)
		    .pageNo(pageNotes.getNumber())
		    .pageSize(pageNotes.getSize())
		    .totalElements(pageNotes.getTotalElements())
		    .totalPages(pageNotes.getTotalPages())
		    .isFirst(pageNotes.isFirst())
		    .isLast(pageNotes.isLast())
		    .build();
		return notesResponse;
	}


	@Override
	public void restoreNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		notes.setIsDeleted(false);
		notes.setDeletedOn(null);
		notesRepo.save(notes);
	}


	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		List<NotesDto> notesDtoList = recycleNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		return notesDtoList;
	}


	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes not found"));
		if (notes.getIsDeleted()) {
			notesRepo.delete(notes);
		} else {
			throw new IllegalArgumentException("Sorry You cant hard delete Directly");
		}
	
		
	}


	@Override
	public void emptyRecycleBin(int userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		if (!CollectionUtils.isEmpty(recycleNotes)) {
			notesRepo.deleteAll(recycleNotes);
		}
	}


	@Override
	public void favoriteNotes(Integer noteId) throws Exception {
		int userId = 2;
		Notes notes = notesRepo.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Notes Not found & Id invalid"));
		FavouriteNote favouriteNote = FavouriteNote.builder().note(notes).userId(userId).build();
		favouriteNoteRepo.save(favouriteNote);
	}


	@Override
	public void unFavoriteNotes(Integer favouriteNoteId) throws Exception {
		FavouriteNote favNote = favouriteNoteRepo.findById(favouriteNoteId)
				.orElseThrow(() -> new ResourceNotFoundException("Favourite Note Not found & Id invalid"));
		favouriteNoteRepo.delete(favNote);
	}


	@Override
	public List<FavouriteNoteDto> getUserFavoriteNotes() throws Exception {
		int userId = 2;
		List<FavouriteNote> favouriteNotes = favouriteNoteRepo.findByUserId(userId);
		return favouriteNotes.stream().map(fn -> mapper.map(fn, FavouriteNoteDto.class)).toList();
	}


	public Boolean copyNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		Notes copyNote = Notes.builder().title(notes.getTitle()).description(notes.getDescription())
				.category(notes.getCategory()).isDeleted(false).fileDetails(null).build();
		
		//  Need to check User Validation
		Notes saveCopyNote = notesRepo.save(copyNote);
		if (!ObjectUtils.isEmpty(saveCopyNote)) {
			return true;
		}
		return false;
	}


	



}
