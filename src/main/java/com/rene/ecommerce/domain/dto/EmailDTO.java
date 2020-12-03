package com.rene.ecommerce.domain.dto;

import java.io.Serializable;

public class EmailDTO implements Serializable{

	
	/**
	 * 
	 */
	
	public EmailDTO() {
		
	}
	
	public EmailDTO(String email) {
		super();
		this.email = email;
	}

	private static final long serialVersionUID = 1L;
	private String email;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
