package com.rene.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.dto.ranking.ClientRankingDTO;
import com.rene.ecommerce.domain.dto.ranking.SellerRankingDTO;
import com.rene.ecommerce.repositories.ClientRepository;
import com.rene.ecommerce.repositories.SellerRepository;

@Service
public class RankingService {

	@Autowired
	private ClientRepository clientRepo;
	

	@Autowired
	private SellerRepository sellerRepo;
	
	public List<ClientRankingDTO> returnRankingClient(){
		
		return clientRepo.returnRankingClient();
	}
	
	public List<SellerRankingDTO> returnRankingSeller(){
		
		return sellerRepo.returnRankingSeller();
	}
	
}
