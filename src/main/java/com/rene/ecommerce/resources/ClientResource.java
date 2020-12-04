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

import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.services.ClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
@Api(value = "Client resource")
@CrossOrigin(origins = "*")
public class ClientResource {

	@Autowired
	private ClientService service;

	@GetMapping("/clients")
	@ApiOperation(value = "Return all clients")
	public ResponseEntity<List<Client>> findAll() {

		return ResponseEntity.ok().body(service.findAll());
	}

	@ApiOperation(value = "Return a client by id")
	@GetMapping("/client/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {

		Client obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	@ApiOperation(value = "Create a client")
	@PostMapping("/create/client")
	public ResponseEntity<Client> insert(@RequestBody Client obj) {

		service.insert(obj);

		return ResponseEntity.ok().body(obj);
	}

	@PutMapping("/update/client")
	@ApiOperation(value = "Update a client ")
	public ResponseEntity<Client> update(@RequestBody Client obj){

		service.update(obj);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping("/delete/client")
	@ApiOperation(value = "Delete a client")
	public ResponseEntity<Void> delete() {
		service.delete();

		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/wishlist/{productId}")
	@ApiOperation(value = "Add a product in your wishlist")
	public ResponseEntity<Void> setProductAsWished(@PathVariable Integer productId){
		service.setProductAsWished(productId);
		return ResponseEntity.noContent().build();

	}

}
