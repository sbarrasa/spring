package com.blink.springboot.dao;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.blink.springboot.entities.Customer;


@Repository
public interface CustomerRedisRepository extends CrudRepository<Customer,Long>{
/*	private RedisTemplate<String, Customer> redis;
	
	public CustomerRedisRepository(RedisTemplate<String, Customer> redisTemplate){
		redis = redisTemplate;
	}
	
	 public Customer save(Customer customer) {
	        redis.opsForHash().put(Customer.class.getSimpleName(), customer.getId(), customer);
	        return customer;
	 }
	*/ 
}
