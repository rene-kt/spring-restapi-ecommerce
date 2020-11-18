package com.rene.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.users.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

}
