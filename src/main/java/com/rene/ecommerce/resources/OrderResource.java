package com.rene.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rene.ecommerce.domain.Order;
import com.rene.ecommerce.services.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Order resource")
@CrossOrigin
@RequestMapping
public class OrderResource {

	@Autowired
	private OrderService service;

	@ApiOperation(value = "Return a client order by id")
	@GetMapping("client/order/{id}")
	public ResponseEntity<Order> findByIdAsClient(@PathVariable Integer id) {

		// true means that the user is a client
		Order obj = service.findById(id, true);
		
		
		return ResponseEntity.ok().body(obj);
	}
	@ApiOperation(value = "Return all client orders")
	@GetMapping("client/orders")
	public ResponseEntity<List<Order>> findAllAsClient() {
		
		// true means that the user is a client
		return ResponseEntity.ok().body(service.findAll(true));
	}
	
	@ApiOperation(value = "Return a seller order by id")
	@GetMapping("seller/order/{id}")
	public ResponseEntity<Order> findByIdAsSeller(@PathVariable Integer id) {

		// false means that the user is a seller
		Order obj = service.findById(id, false);
		
		
		return ResponseEntity.ok().body(obj);
	}
	@ApiOperation(value = "Return all seller orders")
	@GetMapping("seller/orders")
	public ResponseEntity<List<Order>> findAll() {
		
		// false means that the user is a seller
		return ResponseEntity.ok().body(service.findAll(false));
	}
	
	
}
