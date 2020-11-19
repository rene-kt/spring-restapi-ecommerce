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

import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.services.ClientService;


@RestController
@RequestMapping(value = "/ecommerce")
public class ClientResource {

	@Autowired
	private ClientService service;

	@GetMapping("/client/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {

		Client obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/create/client")
	public ResponseEntity<Client> insert(@RequestBody Client obj) {

		service.insert(obj);
	
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping("/client/update")
	public ResponseEntity<Void> update(@RequestBody Client obj){
	
	service.update(obj);
	return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/client/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
