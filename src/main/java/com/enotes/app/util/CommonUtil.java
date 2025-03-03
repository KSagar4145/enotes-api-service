package com.enotes.app.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.enotes.app.handler.GenericResponse;

public class CommonUtil {
	
	public static ResponseEntity<?> createBuildResponse(HttpStatus status,Object data ){
		GenericResponse response = GenericResponse.builder()
				.respoStatus(status)
				.status("success")
				.message("success")
				.data(data)
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(HttpStatus status, String message){
		GenericResponse response = GenericResponse.builder()
				.respoStatus(status)
				.status("success")
				.message(message)
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createErrorResponse(HttpStatus status,Object data){
		GenericResponse response = GenericResponse.builder()
				.respoStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();
		return response.create();
	}

	public static ResponseEntity<?> createErrorResponseMessage(HttpStatus status, String message){
		GenericResponse response = GenericResponse.builder()
				.respoStatus(status)
				.status("failed")
				.message(message)
				.build();
		return response.create();
	}

	public static String getContentType(String originalFileName) {
	    String extension = FilenameUtils.getExtension(originalFileName);

	    switch (extension) {
	        case "pdf":
	            return "application/pdf";
	        case "xlsx":
	            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	        case "txt":
	            return "text/plain";
	        case "png":
	            return "image/png";
	        case "jpeg":
	            return "image/jpeg";
	        default:
	            return "application/octet-stream";
	    }
	}

	
}
