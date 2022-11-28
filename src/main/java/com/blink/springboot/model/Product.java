package com.blink.springboot.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private UUID sku;
	private String name;
	private String description;
	private Integer stock;
	private Double price;
	
	
}
