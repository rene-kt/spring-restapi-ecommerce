package com.rene.ecommerce.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.users.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

	@Transactional
	Seller findByEmail(String email);

	@Modifying
	@Query(value = "select * from tb_sellers order by how_much_money_this_seller_has_sold DESC limit 10 ", nativeQuery = true)
	List<Seller> returnRankingSeller();
}
