package com.rene.ecommerce.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity	
public class Client implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
   
	private String name;
	
    @Column(unique = true)
    private String cpf; 
    
    @Column(unique = true)
    private String email;
    private String password;
    
    @OneToMany(mappedBy = "ownOfTheProduct")
	@JsonIgnore
    private List<Product> ownProducts = new ArrayList<>();
    
    
    

    public List<Product> getOwnProducts() {
		return ownProducts;
	}
	public void setOwnProducts(List<Product> ownProducts) {
		this.ownProducts = ownProducts;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
