package com.rene.ecommerce.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rene.ecommerce.domain.dto.EmailDTO;
import com.rene.ecommerce.services.ForgotPasswordService;

@RestController
@RequestMapping(value = "/ecommerce")
public class AuthResource {

	@Autowired
	private ForgotPasswordService service;

	@PostMapping("/forgot")
	public ResponseEntity<Void> findById(@RequestBody EmailDTO email) {

		service.sendNewPassword(email.getEmail());
		
		return ResponseEntity.ok().build();
	}
	


}
