package com.blink.springboot.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.springboot.entities.Product;

public interface  ProductsRepository extends JpaRepository<Product, Long>{
}
