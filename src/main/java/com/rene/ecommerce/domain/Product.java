package com.rene.ecommerce.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.domain.users.Seller;

@Entity
public class Product implements Serializable {

	public Product() {
		setBuyerOfTheProduct(null);
		setHasBeenSold("Unsold");
	}

	public Product(Integer id, String name, Double price,  Seller productOwner, String description) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.productOwner = productOwner;
		this.description = description;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private Double price;
	private String description;
	

	@Column
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String hasBeenSold;


	@ManyToOne
	@JoinTable(name = "SELLER_PRODUCT", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "seller_id"))
	private Seller productOwner;

	@JsonIgnore
	@ManyToOne
	@JoinTable(name = "CLIENT_PRODUCT", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
	private Client buyerOfTheProduct;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "WISHLIST", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
	private Set<Client> whoWhishesThisProduct;
	
	
	@JsonIgnore
	@OneToOne(mappedBy = "productOrder")
	private Order order;
	

	
	
	
	
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Seller getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(Seller productOwner) {
		this.productOwner = productOwner;
	}

	
	public Client getBuyerOfTheProduct() {
		return buyerOfTheProduct;
	}

	public void setBuyerOfTheProduct(Client buyerOfTheProduct) {
		this.buyerOfTheProduct = buyerOfTheProduct;
	}

	public Set<Client> getWhoWhishesThisProduct() {
		return whoWhishesThisProduct;
	}

	public void setWhoWhishesThisProduct(Set<Client> whoWhishesThisProduct) {
		this.whoWhishesThisProduct = whoWhishesThisProduct;
	}

	

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public String hasBeenSold() {
		return hasBeenSold;
	}

	public void setHasBeenSold(String isSold) {
		this.hasBeenSold = isSold;
	}

	public static boolean isSold(Product obj) {

		if (obj.getBuyerOfTheProduct() != null) {
			
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", productOwner=" + productOwner.getName() + "]";
	}

}
