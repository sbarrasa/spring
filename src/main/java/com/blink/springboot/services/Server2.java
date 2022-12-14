package com.blink.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.CustomerRedis;

@Service
public class Server2 {
	 @Autowired
	 private CircuitBreakerFactory circuitBreakerFactory;
	 
 	 private String server2URI = "http://localhost:8083/customers/";
 	 private RestTemplate rest = new RestTemplate();
	

  	 public CustomerRedis saveCustomer(Customer customer) {
		
		return rest.postForObject(server2URI, customer, CustomerRedis.class);
		
	}


	public Customer getCustomer(Long id) {
		return rest.getForObject(server2URI+"{id}", CustomerRedis.class, id);
	}


	public List<Customer> getAll() {
		ResponseEntity<List<Customer>> response = rest.exchange(server2URI+"all", 
				HttpMethod.GET,
				null, 
				new ParameterizedTypeReference<List<Customer>>() {});

		return response.getBody();

	}
	 
	 

}
