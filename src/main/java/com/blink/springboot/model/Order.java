package com.blink.springboot.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
	private Customer customer;
	@OneToMany(targetEntity = Product.class)
	private Set<Product> products;

	@CreationTimestamp
    private LocalDateTime created;
 
    @UpdateTimestamp
    private LocalDateTime updated;

    public Order() {}

    public Order(Customer customer, Set<Product> products) {
    	this.customer = customer;
    	this.products = products;
    }
    
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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

}
