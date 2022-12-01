package com.blink.springboot.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blink.springboot.dao.CustomersRepository;
import com.blink.springboot.dao.OrdersRepository;
import com.blink.springboot.dao.ProductsRepository;
import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Order;
import com.blink.springboot.entities.Product;
import com.blink.springboot.entities.ProductOrdered;

@Service
public class OrdersManager {
	@Autowired
	public OrdersRepository ordersRepository;
	@Autowired
	public CustomersRepository customersRepository;
	@Autowired
	public  ProductsRepository productsRepository;
	
	public Order save(Long customerId, Set<ProductOrdered> productsOrdered) {
		Customer customer = customersRepository.findById(customerId)
				.orElseThrow(() -> new OrdersError(Customer.class, customerId));
		
		List<Product> products = productsRepository.findAllById(ProductOrdered.getIds(productsOrdered));
		
		ProductOrdered.loadProducts(productsOrdered, products);
	
		productsOrdered.forEach(po -> {
			Product product = po.getProduct();
			if(product.getName() == null)
				throw new OrdersError(String.format("Product.id: %d doesn't exist", product.getId()));
			
			if(product.getStock() != null && po.getCnt() > product.getStock())
				throw new OrdersError(String.format("There are no suficient stock for product.id: %d (max: %d)",
										product.getId(),
										product.getStock())
									 );

			
			product.setStock(product.getStock()-po.getCnt());
			
		});
		
		
		Order order = new Order(customer, productsOrdered);
		
		productsRepository.saveAll(products);
		
		
		return ordersRepository.save(order);
	}

	public Order delete(Long orderId) {
		Order order = ordersRepository.findById(orderId)
				.orElseThrow();
		
		ordersRepository.delete(order);
		
		return order;
	}
	
}
