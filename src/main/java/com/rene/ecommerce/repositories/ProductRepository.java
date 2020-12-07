package com.rene.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Modifying
	@Query(value="delete from wishlist where product_id = :id",nativeQuery = true)
	void removeFromWishList(@Param("id") Integer id);
	

}
