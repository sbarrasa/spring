package com.blink.springboot.entities;


import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
@Table(name = "orders_products")
public class ProductOrdered implements Serializable {
	@Id
    @ManyToOne
	@JsonBackReference
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = true, updatable =true)
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	private Double price=0.0;
	private Integer cnt=0;
	
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Product getProduct() {
		return product;
	}

	public static Set<ProductOrdered> buildSet(List<Product> products,
			Set<ProductOrderedSimple> productsOrderedSimple) {
		
		Set<ProductOrdered> productsOrdered = new HashSet<>();
 		productsOrderedSimple.forEach(pos -> {
			ProductOrdered productOrdered = productsOrdered.stream()
					.filter( po -> po.getProduct().getId().equals(pos.getProductId()))
					.findFirst().orElse(new ProductOrdered());
 			
 			productOrdered.setCnt(productOrdered.getCnt()+pos.getCnt());
 			productOrdered.setPrice(pos.getPrice());
 			
 			productOrdered.product = products.stream()
					.filter( p -> p.getId().equals(pos.getProductId()))
					.findFirst().orElse(new Product(pos.getProductId()));
		 	
 			productsOrdered.add(productOrdered);
		});
 		
 		return productsOrdered;
	}
	
	public Double getPrice() {
		return price;
	}

	public Integer getCnt() {
		return cnt;
	}
	
	public void setPrice(Double price) {
		this.price=price;
	}

	public void setCnt(Integer cnt) {
		this.cnt=cnt;
	}


}

