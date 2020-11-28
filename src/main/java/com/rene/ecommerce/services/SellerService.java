package com.rene.ecommerce.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.exceptions.AuthorizationException;
import com.rene.ecommerce.exceptions.ClientOrSellerHasThisSameEntryException;
import com.rene.ecommerce.exceptions.DuplicateEntryException;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.repositories.ClientRepository;
import com.rene.ecommerce.repositories.SellerRepository;
import com.rene.ecommerce.security.SellerSS;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepo;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Seller findById(Integer id) {

		SellerSS user = UserService.sellerAuthenticated();

		if (user == null || !user.getId().equals(id)) {
			throw new AuthorizationException();
		}
		Optional<Seller> obj = sellerRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException();
		}

	}

	@Transactional
	public Seller insert(Seller obj) {
		obj.setId(null);
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));

		if (clientRepo.findByEmail(obj.getEmail()) == null) {
			try {
				return sellerRepo.save(obj);
			} catch (Exception e) {
				// TODO: handle exception
				throw new DuplicateEntryException();
			}
		}

		throw new ClientOrSellerHasThisSameEntryException("client");

	}

	@Transactional
	public Seller update(Seller obj) {

		SellerSS user = UserService.sellerAuthenticated();

		if (!obj.getId().equals(user.getId())) {
			throw new AuthorizationException();
		}
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));

		try {
			return sellerRepo.save(obj);

		} catch (Exception e) {
			throw new DuplicateEntryException();
		}

	}

	public void delete(Integer id) {
		findById(id);

		try {
			sellerRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("You can't delete this object");
		}
	}

}
