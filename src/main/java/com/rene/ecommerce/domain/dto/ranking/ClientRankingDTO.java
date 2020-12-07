package com.rene.ecommerce.domain.dto.ranking;

public class ClientRankingDTO extends UserDTO{

	
	public ClientRankingDTO(Integer id, String name, Integer buys, Double money) {
		
		setId(id);
		setName(name);
		setSellsOrBuys(buys);
		setMoney(money);
	}
	
	
}
