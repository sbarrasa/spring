package com.blink.springboot.model;





import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProductsRepository extends JpaRepository<Product, Long>{
		
}
