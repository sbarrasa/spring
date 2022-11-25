package com.blink.springboot.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.springboot.model.Customer;
import com.blink.springboot.model.CustomerRepository;
import com.blink.springboot.model.Sex;

@RestController
@RequestMapping("/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/customer/all")
	public List<Customer> getAll(){
		return customerRepository.findAll();
	}		
	
	@PostMapping("/customer")
	public Customer create(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@GetMapping("/customer/")
	public List<Customer> getByLastName(@RequestParam(required = false)  String lastNames, 
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
			
		
		List<Customer> customers = customerRepository.findAll(example)
				.stream().filter(customer -> 
										customer.getAge() >= ageFrom.orElse(0)
										&& 	customer.getAge() <= ageTo.orElse(Integer.MAX_VALUE))
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
}