package com.enotes.app.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enotes.app.entity.Notes;
import com.enotes.app.repo.INotesRepo;

@Component
public class NotesSchedular {
	
//	int i=0;
//	@Scheduled(fixedRate = 1000)
//	public void deleteNotesSchedular() {
//		System.out.println("i="+i++);
//		
//	}
	
	
//	@Autowired
//	private INotesRepo notesRepo;
//
//	@Scheduled(cron = "0 0 0 * * ?")
////	@Scheduled(cron = "* * * ? * *")
//	public void deleteNotesSchdular() {
//		// 20-nov -14 nov -7days
//		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
//		List<Notes> deleteNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
//		notesRepo.deleteAll(deleteNotes);
//	}

}
