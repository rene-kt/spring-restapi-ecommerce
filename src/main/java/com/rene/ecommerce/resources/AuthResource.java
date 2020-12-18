package com.rene.ecommerce.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rene.ecommerce.domain.dto.EmailDTO;
import com.rene.ecommerce.domain.dto.TypeDTO;
import com.rene.ecommerce.services.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Auth service")
@CrossOrigin
@RequestMapping
public class AuthResource {

	@Autowired
	private AuthService service;

	@PostMapping("/forgot")
	@ApiOperation(value = "send a new password to email")
	public ResponseEntity<Void> sendNewPassowrd(@RequestBody EmailDTO email) {

		service.sendNewPassword(email.getEmail());
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/user")
	@ApiOperation(value = "return if the user is a client or a seller")
	public ResponseEntity<TypeDTO> getTypeOfUser() {

		
		return ResponseEntity.ok().body(service.getTypeOfUser());
	}
	


}
