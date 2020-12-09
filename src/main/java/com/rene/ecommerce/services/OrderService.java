package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Order;
import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.repositories.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ClientService clientService;
	
	private Client findClientById(Integer id) {
		return clientService.findById(id);
	}
	public Order findById(Integer id) {
		

		Optional<Order> obj = orderRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException();
		}

	}
	
	public List<Order> findAll(){
		return orderRepo.findAll();
	}
	
	

}
