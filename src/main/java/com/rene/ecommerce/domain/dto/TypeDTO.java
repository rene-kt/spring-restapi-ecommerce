package com.rene.ecommerce.domain.dto;

import java.io.Serializable;

public class TypeDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	
	public TypeDTO() {
		
	}

	public TypeDTO(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
