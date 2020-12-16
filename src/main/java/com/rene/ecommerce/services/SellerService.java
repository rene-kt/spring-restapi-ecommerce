package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.dto.updated.UpdatedSeller;
import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.exceptions.AuthorizationException;
import com.rene.ecommerce.exceptions.ClientOrSellerHasThisSameEntryException;
import com.rene.ecommerce.exceptions.DuplicateEntryException;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.exceptions.UserHasProductsRelationshipsException;
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
	
	public Seller returnClientWithoutParsingTheId() {
		SellerSS user = UserService.sellerAuthenticated();

		if (user == null) {
			throw new AuthorizationException();
		}

		try {
			return findById(user.getId());
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException();
		}

	}

	public List<Seller> findAll() {
		return sellerRepo.findAll();
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
	public Seller update(UpdatedSeller obj) {

		SellerSS user = UserService.sellerAuthenticated();

		Seller sel = findById(user.getId());

		if (user == null || !user.getId().equals(sel.getId())) {
			throw new AuthorizationException();
		}

		sel.setEmail(obj.getEmail());
		sel.setName(obj.getName());
		sel.setPassword(passwordEncoder.encode(obj.getPassword()));

		if (clientRepo.findByEmail(sel.getEmail()) == null) {
			try {
				return sellerRepo.save(sel);
			} catch (Exception e) {
				throw new DuplicateEntryException();
			}
		}

		throw new ClientOrSellerHasThisSameEntryException("client");
	}

	public void delete() {
		SellerSS user = UserService.sellerAuthenticated();

		Seller sel = findById(user.getId());

		if (sel.getNumberOfSells() == 0) {
			sellerRepo.deleteById(user.getId());

		}

		else {
			throw new UserHasProductsRelationshipsException();

		}

	}
}
