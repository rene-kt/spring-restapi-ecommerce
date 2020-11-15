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

import com.rene.ecommerce.domain.Category;
import com.rene.ecommerce.services.CategoryService;

@RestController
@RequestMapping(value = "/ecommerce")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@GetMapping("/category/{id}")
	public ResponseEntity<Category> findById(@PathVariable Integer id) {

		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/category")
	public ResponseEntity<Category> insert(@RequestBody Category obj) {

		service.insert(obj);
	
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping("/category")
	public ResponseEntity<Void> update(@RequestBody Category obj){
	
	service.update(obj);
	return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
