package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Client;
import com.rene.ecommerce.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;

	public Client findById(Integer id) {
		Optional<Client> obj = clientRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}

	}

	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = clientRepo.save(obj);
		return obj;
	}

	@Transactional
	public Client update(Client obj) {
		Client updatedClient = findById(obj.getId());
		clientRepo.save(updatedClient);
		return updatedClient;
	}
	
	public void delete(Integer id) {
		findById(id);
		
		try {
			clientRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("You can't delete this object");
		}
	}
	
	public List<Client> findAll() {
        return clientRepo.findAll();
    }

}
