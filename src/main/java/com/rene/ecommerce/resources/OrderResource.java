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
@CrossOrigin(origins = "*")
@RequestMapping
public class OrderResource {

	@Autowired
	private OrderService service;

	@ApiOperation(value = "Return a order by id")
	@GetMapping("/order/{id}")
	public ResponseEntity<Order> findById(@PathVariable Integer id) {

		Order obj = service.findById(id);
		
		
		return ResponseEntity.ok().body(obj);
	}
	@ApiOperation(value = "Return all orders")
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> findAll() {
		
		return ResponseEntity.ok().body(service.findAll());
	}
	
}
