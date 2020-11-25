package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;
	

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	public Client findById(Integer id) {
		Optional<Client> obj = clientRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException();
		}

	}
	
	public List<Client> findAll(){
		return clientRepo.findAll();
	}

	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		return clientRepo.save(obj);

	}

	@Transactional
	public Client update(Client obj) {
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		return clientRepo.save(obj);
	}

	public void delete(Integer id) {
		findById(id);

		try {
			clientRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("You can't delete this object");
		}
	}

}
