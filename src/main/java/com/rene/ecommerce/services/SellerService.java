package com.rene.ecommerce.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.exceptions.DuplicateEntryException;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.repositories.SellerRepository;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Seller findById(Integer id) {
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

		if (SearchUser.hasAnyClientWithThisEmailOrCpf(obj.getEmail(), obj.getCpf())) {
			throw new DuplicateEntryException();

		}
		return sellerRepo.save(obj);

	}

	@Transactional
	public Seller update(Seller obj) {
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
