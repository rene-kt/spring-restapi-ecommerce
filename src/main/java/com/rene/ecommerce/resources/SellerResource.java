package com.rene.ecommerce.resources;

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

import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.services.SellerService;


@RestController
@RequestMapping(value = "/ecommerce")
public class SellerResource {

	@Autowired
	private SellerService service;

	@GetMapping("/seller/{id}")
	public ResponseEntity<Seller> findById(@PathVariable Integer id) {

		Seller obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/create/seller")
	public ResponseEntity<Seller> insert(@RequestBody Seller obj) {

		service.insert(obj);
	
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping("/seller/update")
	public ResponseEntity<Void> update(@RequestBody Seller obj){
	
	service.update(obj);
	return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/seller/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
