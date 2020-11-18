package com.rene.ecommerce.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SELLERS")
public class Seller extends User {

	
	public Seller() {
		setType("Seller");
	}
	
	@OneToMany(mappedBy = "ownOfTheProduct")
	private List<Product> ownProducts  = new ArrayList<>();
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
}
