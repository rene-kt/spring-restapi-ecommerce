package com.rene.ecommerce.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.users.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

	@Transactional
	Seller findByEmail(String email);
	
	@Transactional
	Seller findByCpf(String cpf);
}
