package com.rene.ecommerce.services;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Product;
import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.repositories.ProductRepository;

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

	public Product findById(Integer id) {
		Optional<Product> obj = productRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}

	}

	@Transactional
	public Product insert(Product obj, Integer sellerId, Integer categoryId) {
		obj.setId(null);
		obj.setOwnOfTheProduct(sellerService.findById(sellerId));
		obj.setCategory(categoryService.findById(categoryId));
		return productRepo.save(obj);

	}

	@Transactional
	public Product update(Product obj) {
		return productRepo.save(obj);
	}

	public void delete(Integer id) throws Exception {

		if (Product.isSold(findById(id))) {
			throw new Exception("O produto já está vendido");
		}

		productRepo.deleteById(id);

	}

	public List<Product> findAll() {
		return productRepo.findAll();
	}

	public Page<Product> findPage(Integer page, Integer line_per_page, String orderBy, String direction) {
		PageRequest page_request = PageRequest.of(page, line_per_page, Direction.valueOf(direction), orderBy);

		return productRepo.findAll(page_request);
	}

	@Transactional
	public Product buyProduct(Integer productId, Integer clientId) {

		Product boughtProduct = findById(productId);
		Client buyer = clientService.findById(clientId);

		buyer.setBoughtProducts(Arrays.asList(boughtProduct));
		boughtProduct.setBuyerOfTheProduct(buyer);

		return productRepo.save(boughtProduct);
	}

}
