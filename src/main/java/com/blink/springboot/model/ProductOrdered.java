package com.blink.springboot.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Embeddable
@Table(name = "orders_products")
@IdClass(ProductOrdered.class)
public class ProductOrdered implements Serializable{
	@Id
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable =false)
	private Order order;
	
	@Id
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable =false)
	private Product product;
	
	public Product getProduct() {
		return product;
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

