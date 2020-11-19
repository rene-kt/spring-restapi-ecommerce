package com.rene.ecommerce.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.users.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	@Transactional
	Client findByEmail(String email);
}
