package com.appsdeveloper.usersapi.oneureka.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloper.usersapi.oneureka.ui.model.CreateUserResponseModel;
import com.appsdeveloper.usersapi.oneureka.ui.model.CreateuserRequestModel;
import com.appsdeveloper.usersapi.oneureka.ui.service.UsersServiceImpl;
import com.appsdeveloper.usersapi.oneureka.ui.shared.UserDto;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UsersServiceImpl service;
	
	@GetMapping("/status/check")
	public String status() {
		return "Working on the port Number: " +env.getProperty("local.server.port");
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateuserRequestModel createUser) {
		
		ModelMapper mapper = new ModelMapper();
		UserDto userDto = mapper.map(createUser, UserDto.class);      //from the request model to the userDto model
		UserDto createdUser = service.createUser(userDto);
		
		CreateUserResponseModel returnValue = mapper.map(createdUser, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
}
