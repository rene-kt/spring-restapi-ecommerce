package com.rene.ecommerce.domain.users;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rene.ecommerce.domain.Order;
import com.rene.ecommerce.domain.Product;

@Entity
@Table(name = "TB_CLIENTS")
public class Client extends User {

	public Client() {
		setType("Client");
		setNumberOfBuys(0);
		setHowMuchMoneyThisClientHasSpent(0.0);
		
	}

	private List<Product> boughtProducts;
	private Set<Product> productsWished;
	private List<Order> orders;

	
	@Column
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer numberOfBuys;
	
	@Column
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double howMuchMoneyThisClientHasSpent;
	
	
	
	

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

	@OneToMany(mappedBy = "buyerOfTheProduct")

	public List<Product> getBoughtProducts() {
		return boughtProducts;
	}

	public void setBoughtProducts(List<Product> boughtProducts) {
		this.boughtProducts = boughtProducts;
	}

	public Integer getNumberOfBuys() {
		return numberOfBuys;
	}

	
	@ManyToMany(mappedBy = "whoWhishesThisProduct")
	public Set<Product> getProductsWished() {
		return productsWished;
	}

	public void setProductsWished(Set<Product> productsWished) {
		this.productsWished = productsWished;
	}
	
	
	
	@OneToMany(mappedBy = "buyer")
	@JsonIgnore
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addNumberOfBuys() {
		this.numberOfBuys = this.numberOfBuys + 1;
	}

	public void setNumberOfBuys(Integer numberOfBuys) {
		this.numberOfBuys = numberOfBuys;
	}

	public Double getHowMuchMoneyThisClientHasSpent() {
		return howMuchMoneyThisClientHasSpent;
	}

	public void setHowMuchMoneyThisClientHasSpent(Double howMuchMoneyThisClientHasSpent) {
		this.howMuchMoneyThisClientHasSpent = howMuchMoneyThisClientHasSpent;
	}
	
	public void addSpentMoneyWhenClientBuyAProduct(Double productPrice) {
		this.howMuchMoneyThisClientHasSpent += productPrice;
	}
			

	
	
	

}
