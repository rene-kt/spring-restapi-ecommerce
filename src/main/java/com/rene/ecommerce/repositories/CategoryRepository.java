package com.rene.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
