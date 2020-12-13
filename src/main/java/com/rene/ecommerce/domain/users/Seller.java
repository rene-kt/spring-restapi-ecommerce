package com.rene.ecommerce.domain.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rene.ecommerce.domain.Order;
import com.rene.ecommerce.domain.Product;

@Entity
@Table(name = "TB_SELLERS")
public class Seller extends User {

	public Seller() {
		setType("Seller");
		setNumberOfSells(0);
		setHowMuchMoneyThisSellerHasSold(0.0);
	}

	private List<Product> ownProducts = new ArrayList<>();
	private List<Order> orders;
	
	@Column
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer numberOfSells;

	@Column
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double howMuchMoneyThisSellerHasSold;
	
	
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

	@Column(unique = true)
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return super.getEmail();
	}

	@Column
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

	@JsonIgnore
	@OneToMany(mappedBy = "productOwner")
	public List<Product> getOwnProducts() {
		return ownProducts;
	}

	public void setOwnProducts(List<Product> ownProducts) {
		this.ownProducts = ownProducts;
	}
	

	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Integer getNumberOfSells() {
		return numberOfSells;
	}

	public void addNumberOfSells() {
		this.numberOfSells = this.numberOfSells + 1;
	}

	public void setNumberOfSells(Integer numberOfSells) {
		this.numberOfSells = numberOfSells;
	}

	public Double getHowMuchMoneyThisSellerHasSold() {
		return howMuchMoneyThisSellerHasSold;
	}

	public void setHowMuchMoneyThisSellerHasSold(Double howMuchMoneyThisSellerHasSold) {
		this.howMuchMoneyThisSellerHasSold = howMuchMoneyThisSellerHasSold;
	}
	
	public void addSoldMoneyWhenSellerSellAProduct(Double productPrice) {
		this.howMuchMoneyThisSellerHasSold += productPrice;
	}
	
	
	
	

}
