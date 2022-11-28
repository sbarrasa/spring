package com.blink.springboot.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity = Customer.class)
	private Customer customer;
	@OneToMany(targetEntity = Product.class)
	private Set<Product> products;

	public Integer getCnt() {
		return products.size();
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<Product> getProduct() {
		return products;
	}
	public void setProduct(Set<Product> product) {
		this.products = product;
	}

	public Double getTotalPrice() {
		return products.stream().mapToDouble(product -> product.getPrice()).sum();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
