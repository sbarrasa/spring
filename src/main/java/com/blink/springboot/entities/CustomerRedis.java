package com.blink.springboot.entities;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
public class CustomerRedis extends Customer {
	public CustomerRedis() {
		super();
	}
	
	public CustomerRedis(Customer customer) {
		super(customer);
		customerId = customer.getId();
	}
	
	
	@Id
	private String redisId;
	
	@Indexed
	private long customerId;
	
	
	public Long getCustomerId() {
		return customerId;
	}
	
	
}
