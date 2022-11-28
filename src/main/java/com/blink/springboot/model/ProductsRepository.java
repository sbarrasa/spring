package com.blink.springboot.model;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface  ProductsRepository extends JpaRepository<Product, Long>{
	@Query( "select p from Product p where id in :ids" )
	Set<Product> findByIds(Set<Long> ids);
}
