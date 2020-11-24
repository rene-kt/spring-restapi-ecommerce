package com.rene.ecommerce.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rene.ecommerce.domain.Product;

public class CategoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private List<Product> products = new ArrayList<>();

	public CategoryDTO() {
		
	}
	
	public CategoryDTO(Integer id, String name, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	


	
	
	
	

	
}
