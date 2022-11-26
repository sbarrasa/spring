package com.blink.springboot.controller;


import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.blink.springboot.model.Customer;
import com.blink.springboot.model.CustomerRepository;
import com.blink.springboot.model.Sex;

@RestController
@RequestMapping("/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	@PostMapping("/customer")
	public Customer create(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}


		
	@RequestMapping(path = "/customer/all", method = RequestMethod.GET)
	public Page<Customer> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size) {
		
	
		
		return customerRepository.findAll(PageRequest.of( 
										page.orElse(0), 
										size.orElse(50),
										Sort.by("lastNames", "names" )));

		
	}

	@GetMapping("/customer/")
	public List<Customer> get(@RequestParam(required = false)  String lastNames, 
							  @RequestParam(required = false) String names,
							  @RequestParam(required = false) Sex sex,
							  @RequestParam(required = false) Optional<Integer> ageFrom,
							  @RequestParam(required = false) Optional<Integer> ageTo) {
		
		Customer customerQuery = new Customer()
				.setLastNames(lastNames)
				.setNames(names)
				.setSex(sex);
		
		Example<Customer> example = Example.of(customerQuery, 
										ExampleMatcher.matching()
											.withIgnoreCase(true)
											.withStringMatcher(StringMatcher.CONTAINING));
			
		List<Customer> customers = customerRepository.findAll(
													example, 
													Sort.by("lastNames", "names"));
		
		customers = customers.stream().filter(customer -> Range.closed(ageFrom.orElse(0),
																       ageTo.orElse(Integer.MAX_VALUE))
														       .contains(customer.getAge()))
										.collect(Collectors.toList());
		
		return customers;
		
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getById(@PathVariable Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow();
		return ResponseEntity.ok(customer);
	}
	
	
	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customerUpdate){
		Optional<Customer> customer = customerRepository.findById(id);
		
		if(customer.isEmpty()) 
			return ResponseEntity.ok(create(customerUpdate));
		
		return ResponseEntity.ok(customerRepository.save(customerUpdate));
	}
	
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Customer> delete(@PathVariable Long id){
		Customer customer = customerRepository.findById(id)
				.orElseThrow();
		
		customerRepository.delete(customer);
		return ResponseEntity.ok(customer);
	}
	
	@GetMapping("/customer/sex")
	public List<Customer> getBySex(@RequestParam Set<Sex> sexs) {
		
		return customerRepository.findBySex(sexs);
	}
	
}