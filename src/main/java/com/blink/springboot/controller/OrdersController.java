package com.blink.springboot.controller;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blink.springboot.model.Order;
import com.blink.springboot.model.OrdersRepository;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersRepository ordersRepository;
	
			
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Page<Order> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size) {
		
	
		
		return ordersRepository.findAll(PageRequest.of( 
										page.orElse(0), 
										size.orElse(50),
										Sort.by("lastNames", "names" )));

		
	}


	@GetMapping("/{id}")
	public ResponseEntity<Order> getById(@PathVariable Long id) {
		Order customer = ordersRepository.findById(id)
				.orElseThrow();
		return ResponseEntity.ok(customer);
	}
	

	@PostMapping("/")
	public Order create(@RequestBody Order order) {
		return ordersRepository.save(order);
	}

	@PutMapping("/{id}")
	public Order update(@PathVariable Long id, @RequestBody Order orderUpdate){
		orderUpdate.setId(id);
		return ordersRepository.save(orderUpdate);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> delete(@PathVariable Long id){
		Order order = ordersRepository.findById(id)
				.orElseThrow();
		
		ordersRepository.delete(order);
		return ResponseEntity.ok(order);
	}
	
		
}