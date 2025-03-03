package com.enotes.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.app.dto.NotesDto;
import com.enotes.app.entity.FileDetails;
import com.enotes.app.entity.Notes;
import com.enotes.app.entity.exceptionhandler.ResourceNotFoundException;
import com.enotes.app.repo.ICategoryRepo;
import com.enotes.app.repo.IFileRepo;
import com.enotes.app.repo.INotesRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

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
		
		categoryRepo.findById(notesDto.getCategory().getId())
		.orElseThrow(()->new ResourceNotFoundException("Invalid Category Id"));
		
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDtls = saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		}else {
			notesMap.setFileDetails(null);
		}
		
		Notes saveNotes = notesRepo.save(notesMap);
		
		return !ObjectUtils.isEmpty(saveNotes)?true:false;
		
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


	



}
