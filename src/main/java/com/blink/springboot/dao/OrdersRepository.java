package com.blink.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.springboot.entities.Order;

public interface OrdersRepository extends JpaRepository<Order, Long>{
	
}