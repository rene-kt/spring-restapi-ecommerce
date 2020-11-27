package com.rene.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.repositories.ClientRepository;
import com.rene.ecommerce.repositories.SellerRepository;

public class SearchUser {

	@Autowired
	private static SellerRepository sellerRepo;

	@Autowired
	private static ClientRepository clientRepo;

// This class is responsible to check if has any email or cpf in both user tables
	// into database
	public static boolean hasAnyClientWithThisEmailOrCpf(String email, String cpf) {

		Client cli = new Client();
		try {
			cli = clientRepo.findByEmail(email);

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		try {

		} catch (NullPointerException e) {
			// TODO: handle exception
			cli = clientRepo.findByCpf(cpf);

		}
		if (cli != null) {
			return true;
		}

		return false;
	}

	public static boolean hasAnySellerWithThisEmailOrCpf(String email, String cpf) {

		Seller sel = new Seller();

		try {
			sel = sellerRepo.findByEmail(email);

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		try {

		} catch (NullPointerException e) {
			// TODO: handle exception
			sel = sellerRepo.findByCpf(cpf);

		}

		if (sel != null) {
			return true;
		}

		return false;
	}

}
