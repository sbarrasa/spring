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

import com.blink.springboot.model.Customer;
import com.blink.springboot.model.CustomersRepository;
import com.blink.springboot.model.Order;
import com.blink.springboot.model.OrdersRepository;
import com.blink.springboot.model.Product;
import com.blink.springboot.model.ProductOrdered;
import com.blink.springboot.model.ProductsRepository;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private CustomersRepository customersRepository;
	@Autowired
	private ProductsRepository productsRepository;
			
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Page<Order> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size) {
		
	
		
		return ordersRepository.findAll(PageRequest.of( 
										page.orElse(0), 
										size.orElse(50),
										Sort.by("updated")));

		
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> getById(@PathVariable Long id) {
		Order order = ordersRepository.findById(id)
				.orElseThrow();
		return ResponseEntity.ok(order);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	@Transactional
	public Order create(@RequestParam Long customer_id,
						@RequestBody Set<ProductOrdered> productsOrdered ) {
		
		Customer customer = customersRepository.findById(customer_id)
				.orElseThrow(() -> new OrdersError(Customer.class, customer_id));
		
		Set<Long> productIDs =  productsOrdered.stream()
										.map(p-> p.getProductId())
										.collect(Collectors.toSet());
				
		List<Product> products = productsRepository.findAllById(productIDs);
		
		productsOrdered.forEach(po -> {
			Product product = products.stream()
					.filter( p -> p.getId().equals(po.getProductId()))
					.findFirst().orElseThrow(() -> new OrdersError(Product.class, po.getProductId())); 

			if(po.getCnt() > product.getStock())
				throw new OrdersError(String.format("There are no suficient stock for product.id: %d (max: %d)",
										product.getId(),
										product.getStock())
									 );

			
			product.setStock(product.getStock()-po.getCnt());
			
		});
		
		
		Order order = new Order(customer, productsOrdered);
		
		productsRepository.saveAll(products);
		
		
		return ordersRepository.save(order);
		
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public Order update(@PathVariable Long id, @RequestBody Order orderUpdate){
		orderUpdate.setId(id);
		return ordersRepository.save(orderUpdate);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> delete(@PathVariable Long id){
		Order order = ordersRepository.findById(id)
				.orElseThrow();
		
		ordersRepository.delete(order);
		return ResponseEntity.ok(order);
	}
	
		
}