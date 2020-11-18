package com.rene.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.User;


@Repository
public interface ClientRepository extends JpaRepository<User, Integer> {

}
