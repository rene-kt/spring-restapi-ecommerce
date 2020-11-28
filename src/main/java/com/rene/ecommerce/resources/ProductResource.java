package com.rene.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rene.ecommerce.domain.Product;
import com.rene.ecommerce.domain.dto.ProductDTO;
import com.rene.ecommerce.exceptions.ProductHasAlreadyBeenSold;
import com.rene.ecommerce.services.ProductService;

@RestController
@RequestMapping(value = "/ecommerce")
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {

		Product obj = service.findById(id);
		ProductDTO dto = new ProductDTO(obj.getId(), obj.getName(), obj.getPrice(), obj.getCategory(),
				obj.getProductOwner(), obj.getBuyerOfTheProduct());
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> findAll() {

		List<Product> products = service.findAll();
		return ResponseEntity.ok().body(products);
	}

	@PostMapping("seller/create/product/{categoryId}")
	public ResponseEntity<Product> insert(@RequestBody ProductDTO obj, @PathVariable Integer categoryId) {

		Product product = new Product(null, obj.getName(), obj.getPrice(), null, null);

		service.insert(product, categoryId);

		return ResponseEntity.ok().body(product);
	}

	@PutMapping("seller/edit/product/{categoryId}")
	public ResponseEntity<Product> update(@RequestBody ProductDTO obj, @PathVariable Integer categoryId,
			@PathVariable Integer sellerId) throws ProductHasAlreadyBeenSold {

		Product product = new Product(obj.getId(), obj.getName(), obj.getPrice(), null, null);

		service.update(product, categoryId);

		return ResponseEntity.ok().body(product);
	}

	@PutMapping("client/buy/product/{productId}")
	public ResponseEntity<Object> buyProduct(@PathVariable Integer productId) {

		try {
			service.buyProduct(productId);
			return ResponseEntity.ok().build();

		} catch (ProductHasAlreadyBeenSold e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("seller/delete/product/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.ok().build();

	}

}
