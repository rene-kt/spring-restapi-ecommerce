package com.rene.ecommerce.domain.dto.updated;

import java.io.Serializable;

public class UpdatedClient implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String cpf;
	private String password;
	
	public UpdatedClient() {
		
	}

	public UpdatedClient(String name, String email, String cpf, String password) {
		super();
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
