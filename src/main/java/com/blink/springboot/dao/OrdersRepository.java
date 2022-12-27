package com.blink.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blink.springboot.entities.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long>{
	
}
