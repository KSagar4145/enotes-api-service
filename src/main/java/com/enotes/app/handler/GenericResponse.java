package com.enotes.app.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse {
	
	private HttpStatus respoStatus;
	private String status;
	private String message;
	private Object data;

	public ResponseEntity<?> create(){
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("status", status);
		map.put("message", message);
		
		if(!ObjectUtils.isEmpty(data)) {
			map.put("data", data);
		}
		
		return  ResponseEntity.status(respoStatus).body(map);
		
	}
	
}
