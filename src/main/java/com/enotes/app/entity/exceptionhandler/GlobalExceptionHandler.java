package com.enotes.app.entity.exceptionhandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<?> handleException(Exception e){
//	log.error("Exception: "+ e.getMessage());
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e){
		log.error("ResourceNotFoundException: "+ e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		log.error("MethodArgumentNotValidException: "+ e.getMessage());
		
		Map<String, String> errMap = new LinkedHashMap<>();
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		allErrors.stream().forEach(err->{
			String msg = err.getDefaultMessage();
			String field = ((FieldError)(err)).getField();
			errMap.put(field, msg);
					
		});
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMap);
	}

}
