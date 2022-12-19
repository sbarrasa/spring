package com.blink.springboot.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.CustomerRedis;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class Server2 {
 	
 	 private String server2URI = "http://localhost:8083/customers/";
 	 private RestTemplate rest = new RestTemplate();
 	private Logger logger = LoggerFactory.getLogger(getClass());

	 @CircuitBreaker(name = "SERVER2", fallbackMethod = "circuitFall")
	 public Customer saveCustomer(Customer customer) {
		return rest.postForObject(server2URI, customer, Customer.class);
		
	 }


 	@CircuitBreaker(name = "SERVER2", fallbackMethod = "circuitFall")
	public Customer getCustomer(Long id) {
		return rest.getForObject(server2URI+"{id}", CustomerRedis.class, id);
	}


    @CircuitBreaker(name = "SERVER2", fallbackMethod = "circuitFallAll")
    public List<Customer> getAll() {
		ResponseEntity<List<Customer>> response = rest.exchange(server2URI+"all", 
						HttpMethod.GET,
						null, 
						new ParameterizedTypeReference<List<Customer>>() {});
		
		return response.getBody();
		
	}
	
    Customer circuitFall(Exception e) {
    	logger.error("Service rest unavilable {}", e.getMessage());
    	return null;
    }
  
    List<Customer> circuitFallAll(Exception e) {
    	logger.error("Service rest unavilable {}", e.getMessage());
    	return List.of();
    }
    
}
