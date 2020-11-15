package com.rene.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
