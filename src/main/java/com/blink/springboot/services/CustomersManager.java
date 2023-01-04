package com.blink.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blink.springboot.dao.CustomersRepository;
import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Sex;

@Service
public class CustomersManager {
	@Autowired
	private CustomersRepository customersRepository;

	private final String defaultOrder = "id";

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<Customer> getAll(){
		return customersRepository.findAll();
	}
			
	public List<Customer> get(String lastNames, 
							  String names,
							  Sex sex,
							  Range<Integer> ages ){
		
		Customer customerQuery = new Customer() 
				.setLastNames(lastNames) 
				.setNames(names) 
				.setSex(sex);
		
				
		Example<Customer> example = Example.of(customerQuery, 
										ExampleMatcher.matching()
											.withIgnoreCase(true)
											.withStringMatcher(StringMatcher.CONTAINING));
		
		Sort sort = Sort.by("lastNames", "names");
		
		List<Customer> customers = customersRepository.findAll(
									example, 
									sort);
		
		return customers.stream()
				.filter(customer -> ages.contains(customer.getAge()))
				.collect(Collectors.toList());
	}

	@Cacheable(value =  "Customer", key = "#id")
	public Customer get(Long id) {
		Customer customer = customersRepository.findById(id)
				.orElseThrow();
		
		logger.info("Getting customer {}", customer);
		
	
		return customer;
	}

	public Page<Customer> getPaginated(Optional<Integer> page, Optional<Integer> size, List<String> orderFields) {
		if(orderFields.isEmpty())
			orderFields.add(defaultOrder);
	
		return customersRepository.findAll(PageRequest.of( 
				page.orElse(0), 
				size.orElse(50),
				Sort.by(orderFields.toArray(new String[0]))));
	}

	public Customer save(Customer customer) {
		logger.info("Saving customer {}", customer);
		
		return customersRepository.save(customer);
	}

	public Customer delete(Long id) {
		Customer customer = customersRepository.findById(id)
				.orElseThrow();
		return delete(customer);
		
		
	}
	
	@Cacheable(value =  "Customer", key = "#id")
	public Customer delete(Customer customer) {
		logger.info("Deleting customer {}", customer);
		
		customersRepository.delete(customer);
		return customer;	
	}

	public List<Customer> get(Set<Sex> sexs) {
		return customersRepository.findBySex(sexs);
	}
}
