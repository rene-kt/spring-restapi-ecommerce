package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Product;
import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.exceptions.AuthorizationException;
import com.rene.ecommerce.exceptions.ClientOrSellerHasThisSameEntryException;
import com.rene.ecommerce.exceptions.DuplicateEntryException;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.exceptions.ProductHasAlreadyBeenSold;
import com.rene.ecommerce.repositories.ClientRepository;
import com.rene.ecommerce.repositories.ProductRepository;
import com.rene.ecommerce.repositories.SellerRepository;
import com.rene.ecommerce.security.ClientSS;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private SellerRepository sellerRepo;
	
	@Autowired
	private ProductRepository productRepo;


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Autowired
	private ProductService productService;


	public Client findById(Integer id) {

		ClientSS user = UserService.clientAuthenticated();

		if (user == null || !user.getId().equals(id)) {
			throw new AuthorizationException();
		}
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
		

		if (sellerRepo.findByEmail(obj.getEmail()) == null) {
			try {
				return clientRepo.save(obj);
			} catch (Exception e) {
				throw new DuplicateEntryException();
			}
		}

		throw new ClientOrSellerHasThisSameEntryException("Seller");

	}

	@Transactional
	public Client update(Client obj) {
		ClientSS user = UserService.clientAuthenticated();
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));


		if (user == null || !user.getId().equals(obj.getId())) {
			throw new AuthorizationException();
		}
		
		if (sellerRepo.findByEmail(obj.getEmail()) == null) {
			try {
				return clientRepo.save(obj);
			} catch (Exception e) {
				throw new DuplicateEntryException();
			}
		}

		throw new ClientOrSellerHasThisSameEntryException("seller");

	}

	public void delete() {
		ClientSS user = UserService.clientAuthenticated();

		try {
			clientRepo.deleteById(user.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("You can't delete this object");
		}
	}
	
	public void setProductAsWished(Integer productId) {
		
		Product product = productService.findById(productId);
		
		if(Product.isSold(product)) {
			throw new ProductHasAlreadyBeenSold();
		}
		
		ClientSS user = UserService.clientAuthenticated();
		Client client = findById(user.getId());
		

		client.getProductsWished().add(product);
		product.getWhoWhishesThisProduct().add(client);
		
		
		clientRepo.save(client);
		productRepo.save(product);
	}

}
