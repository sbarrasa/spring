package com.blink.springboot.entities;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
public class CustomerRedis extends Customer {
	public CustomerRedis() {
		super();
	}
	
	public CustomerRedis(Customer customer) {
		BeanUtils.copyProperties(customer, this);
		customerId = customer.getId();
	}
	
	
	@Id
	private String redisId;
	
	@Indexed
	private Long customerId;
	
	
	public Long getCustomerId() {
		return customerId;
	}
	
	
}
