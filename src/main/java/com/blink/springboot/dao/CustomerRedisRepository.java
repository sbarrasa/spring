package com.blink.springboot.dao;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.CustomerRedis;


@Repository
public interface  CustomerRedisRepository extends JpaRepository<CustomerRedis,String>{
	default Customer findById(Long id) {
		return new Customer(findBycustomerId(id).orElseThrow());
	}
	
	Optional<CustomerRedis> findBycustomerId(Long id);
}

