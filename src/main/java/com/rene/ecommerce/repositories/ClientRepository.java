package com.rene.ecommerce.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rene.ecommerce.domain.users.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	@Transactional
	Client findByEmail(String email);
	
	@Modifying
	@Query(value="select * from tb_clients order by how_Much_Money_This_Client_Has_Spent DESC limit 10 ",nativeQuery = true)
	List<Client> returnRankingClient();
}
