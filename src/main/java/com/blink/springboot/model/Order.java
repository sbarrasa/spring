package com.blink.springboot.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Customer customer;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.PERSIST)
	private Set<ProductOrdered> productsOrdered ;

	@CreationTimestamp
    private LocalDateTime created;
 
    @UpdateTimestamp
    private LocalDateTime updated;

    public Order() {}

    public Order(Customer customer, Set<ProductOrdered> productsOrdered) {
    	setCustomer(customer);
    	setProductsOrdered(productsOrdered);
    }
    

	public CustomerSimple getCustomer() {
		return new CustomerSimple(customer);
	}
	
	
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		
	}
	

	public Integer getCntItems() {
		return productsOrdered.stream().mapToInt(product -> product.getCnt()).sum();
	}

	public Double getTotalPrice() { 
		return productsOrdered.stream().mapToDouble(product -> product.getPrice()* product.getCnt()).sum() ;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Set<ProductOrdered> getProductsOrdered() {
		return productsOrdered;
	}

	public void setProductsOrdered(Set<ProductOrdered> productsOrdered) {
		this.productsOrdered = productsOrdered;
		productsOrdered.forEach(p -> p.setOrder(this));
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
