package com.blink.springboot.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Sex;

import java.util.List;
import java.util.Set; 

public interface CustomersRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT c "
		+ "FROM Customer c "
		+ "WHERE sex in :sexs ")
	List<Customer> findBySex(Set<Sex> sexs);
	
	//<T> List<T> findAll(Class<T> type, Sort sort);
}
