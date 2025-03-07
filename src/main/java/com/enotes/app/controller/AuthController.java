package com.enotes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.app.dto.UserDto;
import com.enotes.app.service.IUserService;
import com.enotes.app.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

	@Autowired
	private IUserService userService;

	@PostMapping("/addUser")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
		Boolean register = userService.register(userDto);
		if (register) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "User Register success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "User Register failed");
	}

}
