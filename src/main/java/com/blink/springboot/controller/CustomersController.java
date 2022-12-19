package com.blink.springboot.controller;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.blink.springboot.dao.CustomersRepository;
import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Sex;
import com.blink.springboot.services.Server2;

@RestController
@RequestMapping("/customers")
public class CustomersController {
	private final String defaultOrder = "id";

	@Value("${kafka.topic.name}") 
	private String kafkaTopic;
	
	@Autowired
	private KafkaTemplate<String, Customer> kafkaTemplate;
	
	@Autowired
	private CustomersRepository customersRepository;

	@Autowired
	private Server2 server2 ;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	 /*	@Autowired
	private CustomerRedisRepository customersRedisRepository;
*/	

	
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Page<Customer> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size,
			 				  @RequestParam(required = false, defaultValue = defaultOrder  ) List<String> orderFields) {
		
		if(orderFields.isEmpty())
			orderFields.add(defaultOrder);
		
		return customersRepository.findAll(PageRequest.of( 
										page.orElse(0), 
										size.orElse(50),
										Sort.by(orderFields.toArray(new String[0]))));
		
		//								Sort.by("lastNames", "names" )));

		
	}

	@GetMapping("/")
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
		
		Sort sort = Sort.by("lastNames", "names");
		
		List<Customer> customers = customersRepository.findAll(
									example, 
									sort);
		
		return customers.stream()
				.filter(customer -> Range.closed(ageFrom.orElse(0),
			  						         ageTo.orElse(Integer.MAX_VALUE))
											.contains(customer.getAge()))
				.collect(Collectors.toList());
		
		
	}

	@Cacheable(value =  "Customer", key = "#id")
	@GetMapping("/{id}")
	public Customer getById(@PathVariable Long id) {
		Customer customer = customersRepository.findById(id)
							.orElseThrow();
		
		logger.info("Getting customer {}", customer);
		
		return customer;
	}

	@PostMapping("/")
	public Customer create(@RequestBody Customer customer) {
		logger.info("Saving customer {}", customer);
		
		return customersRepository.save(customer);
	}

	@CachePut(value =  "Customer", key = "#id")
	@PutMapping("/{id}")
	public Customer update(@PathVariable Long id, @RequestBody Customer customer){
		customer.setId(id);
		logger.info("Saving customer {}", customer);

		return customersRepository.save(customer);	
	}

	@CacheEvict(value =  "Customer", key = "#id")
	@DeleteMapping("/{id}")
	public ResponseEntity<Customer> delete(@PathVariable Long id){
		Customer customer = customersRepository.findById(id)
				.orElseThrow();
		logger.info("Deleting customer {}", customer);
		
		customersRepository.delete(customer);
		return ResponseEntity.ok(customer);
	}
	
	@GetMapping("/sex")
	public List<Customer> getBySex(@RequestParam Set<Sex> sexs) {
		return customersRepository.findBySex(sexs);
	}
	
	@PutMapping("/kafka/{id}")
	public Customer putInKafka(@PathVariable Long id){
		Customer customer = getById(id);
		
		return putInKafka(customer);	
	}

	@PutMapping("/kafka/")
	public Customer putInKafka(@RequestBody Customer customer){
		logger.info("Putting custumer {} in kafka topic {}", kafkaTopic, customer);

		kafkaTemplate.send(kafkaTopic, customer);
		return customer;	
	}
	
	@PostMapping("/server2/{id}")
	public Customer saveServer2Customer(@RequestParam Long id) {
		Customer customer = getById(id);
		return server2.saveCustomer(customer);
		
	}


	@PutMapping("/server2/")
	public Customer saveServer2Customer(@RequestBody Customer customer) {
		return server2.saveCustomer(customer);
		
		
	}
	
	@GetMapping("/server2/{id}")
	public Customer getServer2Customer(@PathParam("id") Long id) {
		
		return server2.getCustomer(id);
		
	}

	@GetMapping("/server2/all")
	public List<Customer> getServer2CustomerAll() {
		return server2.getAll();
		
	}

/*	@PostMapping("/redis/{id}")
	public Customer saveToRedis(@PathVariable Long id) {
		CustomerRedis customer = customersRedisRepository.findByCustomerId(id)
				                     .orElseGet( () -> new CustomerRedis(customersRepository.findById(id)
				                     .orElseThrow()));
				
		return customersRedisRepository.save(customer);
	}
	
	@GetMapping("/redis/all")
	public Iterable<CustomerRedis> getAllFromRedis() { 
		return customersRedisRepository.findAll(Sort.by("id"));
	} 
	
	@GetMapping("/redis/{id}")
	public Customer getFromRedis(@PathVariable Long id) {
		return customersRedisRepository.findByCustomerId(id).orElseThrow();
	}
*/	
	
}