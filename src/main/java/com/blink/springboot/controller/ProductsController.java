package com.blink.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.springboot.model.Product;
import com.blink.springboot.model.ProductsRepository;

@RestController
@RequestMapping("/products")
public class ProductsController {
	@Autowired
	private ProductsRepository productsRepository;

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Page<Product> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size) {
		
	
		
		return productsRepository.findAll(PageRequest.of( 
										page.orElse(0), 
										size.orElse(50),
										Sort.by("name")));

		
	}
	
	@GetMapping("/sku")
	public Product getBySku(Long id) {
		return productsRepository.findById(id).orElseThrow();
	}
	
	@RequestMapping(path = "/list", method=RequestMethod.POST)
	public List<Product> createBatch(@RequestBody List<Product> products) {
		return productsRepository.saveAll(products);
	}

	@RequestMapping(path = "/", method=RequestMethod.POST)
	public Product create(@RequestBody Product product) {
		return productsRepository.save(product);
	}

	@RequestMapping(path = "/{id}", method=RequestMethod.PUT)
	public Product update(@PathVariable Long id, @RequestBody Product product) {
		product.setId(id);
		
		return productsRepository.save(product);
	}
	
	@RequestMapping(path = "/{id}", method=RequestMethod.DELETE)
	public Product delete(@PathVariable Long id) {
		Product product = productsRepository.findById(id).orElseThrow();
		
		productsRepository.delete(product);
		
		return product;
		
	}
	

}
