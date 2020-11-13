package com.rene.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
