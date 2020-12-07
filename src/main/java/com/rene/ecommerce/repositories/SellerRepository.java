package com.rene.ecommerce.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.dto.ranking.SellerRankingDTO;
import com.rene.ecommerce.domain.users.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

	@Transactional
	Seller findByEmail(String email);
	
	@Transactional
	Seller findByCpf(String cpf);
	

	@Modifying
	@Query(value="select id, name, numberOfSells,   howMuchMoneyThisSellerHasSold from tb_sellers order by howMuchMoneyThisSellerHasSold DESC limit 10 ",nativeQuery = true)
	List<SellerRankingDTO> returnRankingSeller();
}
