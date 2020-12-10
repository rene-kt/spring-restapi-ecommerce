package com.rene.ecommerce.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.domain.users.Seller;

@Entity(name = "tb_order")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String instant;
	
	

	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@OneToOne
	@JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Product productOrder;

	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToOne
	@JoinTable(name = "order_client", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
	private Client buyer;

	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToOne
	@JoinTable(name = "order_seller", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "seller_id"))
	private Seller seller;
	
	
	public Order() {
		
	}
	
	
	
	public Order(Integer id, String instant, Product product) {
		this.id = id;
		this.instant = instant;
		this.productOrder = product;
		this.buyer = product.getBuyerOfTheProduct();
		this.seller = product.getProductOwner();
	}



	public String getInstant() {
		return instant;
	}
	public void setInstant(String instant) {
		this.instant = instant;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	
	
	public Client getBuyer() {
		return buyer;
	}



	public void setBuyer(Client buyer) {
		this.buyer = buyer;
	}



	@JsonIgnore
	public Seller getSeller() {
		return seller;
	}



	public void setSeller(Seller seller) {
		this.seller = seller;
	}



	public Product getProductOrder() {
		return productOrder;
	}



	public void setProductOrder(Product product) {
		this.productOrder = product;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stu

		return buyer.getName() + seller.getName();
	}
	

	
	
}
