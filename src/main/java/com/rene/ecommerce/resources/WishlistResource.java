package com.rene.ecommerce.resources;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rene.ecommerce.domain.Product;
import com.rene.ecommerce.services.WishlistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Wishlist resource")
@CrossOrigin
@RequestMapping
public class WishlistResource {

	@Autowired
	private WishlistService service;

	@ApiOperation(value = "Return user's wishlist")
	@GetMapping("/wishlist")
	public ResponseEntity<Set<Product>> returnWishlist() {

		
		return ResponseEntity.ok().body(service.findAll());
	}

	@PostMapping("/wishlist/{productId}")
	@ApiOperation(value = "Mark product as wished")
	public ResponseEntity<Void> maskProductAsWished(@PathVariable Integer productId) {

		service.markProductAsWished(productId);
	
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/wishlist/{productId}")
	@ApiOperation(value = "Delete a product from wishlist")
	public ResponseEntity<Void> delete(@PathVariable Integer productId){
		service.delete(productId);
	
		return ResponseEntity.noContent().build();
	}

}
