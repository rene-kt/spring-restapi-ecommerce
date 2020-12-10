package com.rene.ecommerce.domain.dto.ranking;

public abstract class UserDTO {
	
	private Integer id;
	private String name;
	private Integer sellsOrBuys;
	private Double money;
	
	public UserDTO() {
		
	}
	
	
	public UserDTO(Integer id, String name, Integer sellsOrBuys, Double money) {
		super();
		this.id = id;
		this.name = name;
		this.sellsOrBuys = sellsOrBuys;
		this.money = money;
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
	public Integer getSellsOrBuys() {
		return sellsOrBuys;
	}
	public void setSellsOrBuys(Integer sellsOrBuys) {
		this.sellsOrBuys = sellsOrBuys;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	
	
}
