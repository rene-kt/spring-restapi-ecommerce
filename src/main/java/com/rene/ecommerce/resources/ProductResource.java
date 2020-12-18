package com.rene.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.rene.ecommerce.domain.dto.updated.UpdatedProduct;
import com.rene.ecommerce.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
@Api(value = "Product resource")
@CrossOrigin
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping("/product/{id}")
	@ApiOperation(value = "Return a product by id")
	public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {

		Product obj = service.findById(id);
		ProductDTO dto = new ProductDTO(obj.getId(), obj.getName(), obj.getPrice(), obj.getProductOwner(),
				obj.getBuyerOfTheProduct(), obj.getDescription());
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("/products")
	@ApiOperation(value = "Return all products")

	public ResponseEntity<List<Product>> findAll() {

		List<Product> products = service.findAll();
		return ResponseEntity.ok().body(products);
	}
	

	@GetMapping("/ownproducts")
	@ApiOperation(value = "Return own products")

	public ResponseEntity<List<Product>> findOwnProducts() {

		List<Product> products = service.findOwnProducts();
		return ResponseEntity.ok().body(products);
	}


	@ApiOperation(value = "Create a product")
	@PostMapping("/product")
	public ResponseEntity<Product> insert(@RequestBody ProductDTO obj) {

		Product product = new Product(null, obj.getName(), obj.getPrice(), null, obj.getDescription());

		service.insert(product);

		return ResponseEntity.ok().body(product);
	}

	@ApiOperation(value = "Update a product")
	@PutMapping("/product/{productId}")
	public ResponseEntity<Product> update(@RequestBody UpdatedProduct obj,
			@PathVariable Integer productId) {

		Product product = service.update(obj, productId);

		return ResponseEntity.ok().body(product);
	}

	@ApiOperation(value = "Buy a product and send a confirmation email to client and to the seller")
	@PutMapping("buy/{productId}")
	public ResponseEntity<Void> buyProduct(@PathVariable Integer productId) {

		service.buyProduct(productId);
		return ResponseEntity.ok().build();

	}

	@ApiOperation(value = "Delete a product")
	@DeleteMapping("product/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.ok().build();

	}

}
