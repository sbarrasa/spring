package com.blink.springboot.entities;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductOrderedSimple {
	Long productId;
	Integer cnt = 0;
	Double price = 0.0;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public static Set<Long> getIds(Set<ProductOrderedSimple> products) {
		return products.stream()
				.map(ProductOrderedSimple::getProductId)
				.collect(Collectors.toSet());
	}
	
}
