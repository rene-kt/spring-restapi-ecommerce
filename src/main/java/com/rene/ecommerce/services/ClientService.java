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
import com.rene.ecommerce.exceptions.ClientOrSellerHasThisSameEntryException;
import com.rene.ecommerce.exceptions.DuplicateEntryException;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.repositories.ClientRepository;
import com.rene.ecommerce.repositories.SellerRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private SellerRepository sellerRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SellerRepository sellerReppo;

	public Client findById(Integer id) {
		Optional<Client> obj = clientRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException();
		}

	}

	public List<Client> findAll() {
		return clientRepo.findAll();
	}

	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));

		
			if(sellerRepo.findByEmail(obj.getEmail()) == null) {
				try {
					return clientRepo.save(obj);
				} catch (Exception e) {
					// TODO: handle exception
					throw new DuplicateEntryException();
				}
			}
			
			throw new ClientOrSellerHasThisSameEntryException("Seller");
			
	

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

	public boolean hasAnyUserWithThisEmail(String email) {
		if (sellerReppo.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
}
