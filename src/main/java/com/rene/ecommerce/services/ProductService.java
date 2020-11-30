package com.rene.ecommerce.services;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Product;
import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.exceptions.AuthorizationException;
import com.rene.ecommerce.exceptions.ObjectNotFoundException;
import com.rene.ecommerce.exceptions.ProductHasAlreadyBeenSold;
import com.rene.ecommerce.repositories.ProductRepository;
import com.rene.ecommerce.security.ClientSS;
import com.rene.ecommerce.security.SellerSS;
import com.rene.ecommerce.services.email.EmailService;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EmailService emailService;

	public Product findById(Integer id) {
		Optional<Product> obj = productRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException();
		}

	}

	@Transactional
	public Product insert(Product obj, Integer categoryId) {

		SellerSS user = UserService.sellerAuthenticated();

		obj.setId(null);
		obj.setProductOwner(sellerService.findById(user.getId()));
		obj.setCategory(categoryService.findById(categoryId));
		return productRepo.save(obj);

	}

	// verify if its product owner
	@Transactional
	public Product update(Product obj, Integer categoryId) {
		SellerSS user = UserService.sellerAuthenticated();
		Seller seller = sellerService.findById(user.getId());

		if (!obj.getProductOwner().equals(seller)) {
			throw new AuthorizationException("You're not owner of this product");
		}
		if (Product.isSold(findById(obj.getId()))) {
			throw new ProductHasAlreadyBeenSold();
		}

		obj.setProductOwner(seller);
		obj.setCategory(categoryService.findById(categoryId));
		return productRepo.save(obj);

	}

	public void delete(Integer id) {
		SellerSS user = UserService.sellerAuthenticated();
		Seller seller = sellerService.findById(user.getId());
		Product obj = findById(id);

		if (!obj.getProductOwner().equals(seller)) {
			throw new AuthorizationException("You're not owner of this product");
		}
		if (Product.isSold(findById(id))) {
			throw new ProductHasAlreadyBeenSold();
		}
		productRepo.deleteById(id);

	}

	public List<Product> findAll() {
		return productRepo.findAll();
	}

	@Transactional
	public Product buyProduct(Integer productId) {

		if (Product.isSold(findById(productId))) {
			throw new ProductHasAlreadyBeenSold();
		}

		ClientSS user = UserService.clientAuthenticated();
		Client buyer = clientService.findById(user.getId());
		Product boughtProduct = findById(productId);

		buyer.setBoughtProducts(Arrays.asList(boughtProduct));
		boughtProduct.setBuyerOfTheProduct(buyer);

		// method to add: number of buys and sells | money spent and sold
		addNumberOfBuysAndSellsAndMoneySoldAndSpent(boughtProduct, buyer);
		
		// thread to send email
		threadSendEmail(boughtProduct);

		return productRepo.save(boughtProduct);
	}

	private void threadSendEmail(Product boughtProduct) {
		Thread threadEmail = new Thread() {
			public void run() {
				emailService.sendConfirmationEmailHtml(boughtProduct);

			}
		};
		threadEmail.start();
	}
	
	private void addNumberOfBuysAndSellsAndMoneySoldAndSpent(Product boughtProduct, Client buyer) {
		Seller sel = boughtProduct.getProductOwner();
		Double price = boughtProduct.getPrice();
		
		buyer.addNumberOfBuys();
		sel.addNumberOfSells();
		
		buyer.addSpentMoneyWhenClientBuyAProduct(price);
		sel.addSoldMoneyWhenSellerSellAProduct(price);
	
		
	}
	



}
