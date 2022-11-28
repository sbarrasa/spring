package com.blink.springboot.controller;

import com.blink.springboot.model.Customer;

public class OrdersError extends Error {
	
	public OrdersError(Customer customer ) {
		this(String.format("Customer %d not found", customer.getId()));
	}
	
	public OrdersError(String msg) {
		super(msg);
	}
}
