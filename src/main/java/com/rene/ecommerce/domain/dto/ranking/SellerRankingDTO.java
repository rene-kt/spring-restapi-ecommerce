package com.rene.ecommerce.domain.dto.ranking;

public class SellerRankingDTO extends UserDTO{

	public SellerRankingDTO(Integer id, String name, Integer sells, Double money) {
		
		setId(id);
		setName(name);
		setSellsOrBuys(sells);
		setMoney(money);
	}
}
