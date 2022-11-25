package com.blink.springboot.model;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByLastNamesLikeIgnoreCase(String lastname);
}
