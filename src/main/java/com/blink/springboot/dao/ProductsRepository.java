package com.blink.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.springboot.entities.Product;

public interface  ProductsRepository extends JpaRepository<Product, Long>{
}
