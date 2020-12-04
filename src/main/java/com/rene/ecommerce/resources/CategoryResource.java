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

import com.rene.ecommerce.domain.Category;
import com.rene.ecommerce.domain.dto.CategoryDTO;
import com.rene.ecommerce.services.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Category resource")
@CrossOrigin(origins = "*")
@RequestMapping
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@ApiOperation(value = "Return a category by id")
	@GetMapping("category/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Integer id) {

		Category obj = service.findById(id);
		
		CategoryDTO category = new CategoryDTO(obj.getId(),obj.getName(), obj.getProducts());
		
		return ResponseEntity.ok().body(category);
	}
	@ApiOperation(value = "Return all categories")
	@GetMapping("categories")
	public ResponseEntity<List<Category>> findAll() {
		
		return ResponseEntity.ok().body(service.findAll());
	}
	

	@PostMapping("category")
	@ApiOperation(value = "Create a category")
	public ResponseEntity<Category> insert(@RequestBody Category obj) {

		service.insert(obj);
	
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping("category")
	@ApiOperation(value = "Edit a category")
	public ResponseEntity<Void> update(@RequestBody Category obj){
	
	service.update(obj);
	return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("category/{id}")
	@ApiOperation(value = "Delete a category")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
