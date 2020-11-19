package com.rene.ecommerce.domain.users;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rene.ecommerce.domain.Product;

@Entity
@Table(name = "TB_CLIENTS")
public class Client extends User {
	
	
	public Client() {
		setType("Client");
	}
	
	
	
	private List<Product> boughtProducts;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Column
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Column

	@Override
	public String getCpf() {
		// TODO Auto-generated method stub
		return super.getCpf();
	}

	@Column

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return super.getEmail();
	}

	@Column

	@JsonIgnore
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

	@Column
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return super.getType();
	}

	@OneToMany(mappedBy = "buyerOfTheProduct")

	public List<Product> getBoughtProducts() {
		return boughtProducts;
	}

	public void setBoughtProducts(List<Product> boughtProducts) {
		this.boughtProducts = boughtProducts;
	}


	
	
}
