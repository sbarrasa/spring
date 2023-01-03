package com.blink.springboot.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blink.springboot.entities.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class Server2 {
 	
	 @Value("${com.blink.springboot.server2.uri}")
 	 private String uri;
	 
 	 private RestTemplate rest = new RestTemplate();
 	 
 	 private Logger logger = LoggerFactory.getLogger(getClass());

//	 @CircuitBreaker(name = "SERVER2", fallbackMethod = "circuitFall" )
	 public Customer saveCustomer(Customer customer) {
		return rest.postForObject(uri, customer, Customer.class);
	 }


 	//@CircuitBreaker(name = "SERVER2", fallbackMethod = "circuitFall")
	public Customer getCustomer(Long id) {
 		String uriGetCustomer = this.uri+"{id}";
		return rest.getForObject(uriGetCustomer, Customer.class, id);
	}


//    @CircuitBreaker(name = "SERVER2", fallbackMethod = "circuitFallAll")
    public List<Customer> getAll() {
    	String uriGetAll = uri+"all";
		return rest.exchange(uriGetAll, 
						HttpMethod.GET,
						null, 
						new ParameterizedTypeReference<List<Customer>>() {})
						.getBody();
		
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
