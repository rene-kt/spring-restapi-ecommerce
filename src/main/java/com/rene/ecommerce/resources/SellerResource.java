package com.rene.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rene.ecommerce.domain.dto.ranking.SellerRankingDTO;
import com.rene.ecommerce.domain.dto.updated.UpdatedSeller;
import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.services.RankingService;
import com.rene.ecommerce.services.SellerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
@Api(value = "Client resource")
@CrossOrigin
public class SellerResource {

	@Autowired
	private SellerService service;

	@Autowired
	private RankingService ranking;

	@ApiOperation(value = "Return your own profile as Seller")
	@GetMapping("/seller")
	public ResponseEntity<Seller> find() {

		Seller obj = service.returnClientWithoutParsingTheId();
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/sellers")
	@ApiOperation(value = "Return all seller")
	public ResponseEntity<List<Seller>> findAll() {

		return ResponseEntity.ok().body(service.findAll());
	}

	@ApiOperation(value = "Create a seller")
	@PostMapping("/create/seller")
	public ResponseEntity<Seller> insert(@RequestBody Seller obj) {

		service.insert(obj);

		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Update a seller")
	@PutMapping("/update/seller")
	public ResponseEntity<Seller> update(@RequestBody UpdatedSeller obj) {

		Seller sel = service.update(obj);
		return ResponseEntity.ok().body(sel);
	}

	@ApiOperation(value = "Delete a seller")
	@DeleteMapping("/delete/seller")
	public ResponseEntity<Void> delete() {
		service.delete();

		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Return a list of sellers who sells the most")
	@GetMapping("/sellers/ranking")
	public ResponseEntity<List<SellerRankingDTO>> returnRankingSeller() {

		return ResponseEntity.ok().body(ranking.returnRankingSeller());
	}

}
