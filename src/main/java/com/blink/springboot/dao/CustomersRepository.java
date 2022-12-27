package com.blink.springboot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Sex;

import java.util.List;
import java.util.Set; 

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT c "
		+ "FROM Customer c "
		+ "WHERE sex in :sexs ")
	public List<Customer> findBySex(Set<Sex> sexs);
	
}
