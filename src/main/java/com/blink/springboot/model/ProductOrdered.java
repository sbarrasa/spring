package com.blink.springboot.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "orders_products")
@IdClass(ProductOrdered.class)
public class ProductOrdered implements Serializable{
	@Id
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable =false)
	private Order order;
	
	@Id
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	
	@JsonIgnore
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	public Product getProduct() {
		if(product == null)
			product = new Product(); 
		return product;
	}
	
	public void setProductId(Long product_id) {
		getProduct().setId(product_id);
	}
	
	public Long getProductId() {
		return getProduct().getId();
	}
	
	private Double price = null;
	private Integer cnt = 1;
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	
}

