package com.rene.ecommerce.domain.dto.updated;

import java.io.Serializable;

public class UpdatedProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Double price;
	private String description;
	
	public UpdatedProduct() {
		
	}
	

	public UpdatedProduct(String name, Double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
