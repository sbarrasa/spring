package com.blink.springboot.controller;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Order;
import com.blink.springboot.entities.Product;
import com.blink.springboot.entities.ProductOrdered;
import com.blink.springboot.entities.ProductOrderedSimple;
import com.blink.springboot.model.CustomersRepository;
import com.blink.springboot.model.OrdersRepository;
import com.blink.springboot.model.ProductsRepository;
import com.blink.springboot.services.OrdersManager;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	@Autowired 
	private OrdersManager ordersManager;
	
			
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Page<Order> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size) {
		
	
		
		return ordersManager.ordersRepository.findAll(PageRequest.of( 
										page.orElse(0), 
										size.orElse(50),
										Sort.by("updated")));

		
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> getById(@PathVariable Long id) {
		Order order = ordersManager.ordersRepository.findById(id)
				.orElseThrow();
		return ResponseEntity.ok(order);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	@Transactional
	public Order create(@RequestParam Long customerId,
						@RequestBody Set<ProductOrderedSimple> productsOrderedSimple ) {
		
		return ordersManager.save(customerId, productsOrderedSimple);
		
			
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public Order update(@PathVariable Long id, @RequestBody Order orderUpdate){
		orderUpdate.setId(id);
		return ordersManager.ordersRepository.save(orderUpdate);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> delete(@PathVariable Long orderId){
		Order order = ordersManager.delete(orderId);
		
		return ResponseEntity.ok(order);
	}
	
		
}