package com.blink.springboot.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;




@Entity
@Table(name = "orders_products")
public class ProductOrdered implements Serializable{

	@Id
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = true, updatable =true)
	private Order order;

	@JsonBackReference
	public Order getOrder() {
		return order;
	}

	@JsonBackReference
	public void setOrder(Order order) {
		this.order = order;
	}

	@Id
	@Column(name = "product_id")
	private Long productId;
	
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getProductId() {
		return productId;
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

