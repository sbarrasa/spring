package com.blink.springboot.controller;


import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.View;
import org.springframework.web.servlet.ModelAndView;

import com.blink.springboot.entities.Customer;
import com.blink.springboot.entities.Sex;
import com.blink.springboot.services.CustomersManager;
import com.blink.springboot.services.DemoService;
import com.blink.springboot.services.Server2;

@RestController
@RequestMapping("/customers")
public class CustomersController {

	@Value("${kafka.topic.name}") 
	private String kafkaTopic;

	@Autowired
	private DemoService demo;
	
	@Autowired
	private KafkaTemplate<String, Customer> kafkaTemplate;

	@Autowired
	private CustomersManager customersManager;

	@Autowired
	private Server2 server2 ;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	


	@GetMapping("/show/{id}")
	public ModelAndView show(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView();

	    mav.addObject("customer", customersManager.get(id));
	    mav.setViewName("customer");

        return mav;
	}
	
	@GetMapping("/show/")
	public ModelAndView showAll() {
		ModelAndView mav = new ModelAndView();
		
  	  	mav.setViewName("customers");

		List<Customer> customers = customersManager.getAll();
		
		Customer prueba = customers.get(new Random().nextInt(customers.size()-1));
		Integer money = new Random().nextInt(2000)-1000;
		mav.addObject( "prueba", prueba);
		mav.addObject( "money", money);
		
		mav.addObject("customers", customers);
	  
        return mav;
	}
	
	
	
	@PostMapping("/demo")
	public DemoService setDemo(@RequestBody DemoService demoUpdate) {
		BeanUtils.copyProperties(demoUpdate, demo);
		return this.demo;
	}
	
	@GetMapping("/demo")
	public DemoService getDemo() {
		return demo;
	}
	
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Page<Customer> getAll(@RequestParam(required = false) Optional<Integer> page,
			 				  @RequestParam(required = false) Optional<Integer> size,
			 				  @RequestParam(required = false) List<String> orderFields) {
		
		
		
		return customersManager.getPaginated(page, size, orderFields);
		
			
	}

	@GetMapping("/")
	public List<Customer> get(@RequestParam(required = false) String lastNames, 
							  @RequestParam(required = false) String names,
							  @RequestParam(required = false) Sex sex,
							  @RequestParam(required = false) Optional<Integer> ageFrom,
							  @RequestParam(required = false) Optional<Integer> ageTo) {
		
		return customersManager.get(lastNames, names, sex, 
										Range.closed(ageFrom.orElse(0),
													 ageTo.orElse(Integer.MAX_VALUE)));
		
		
	}

	@GetMapping("/{id}")
	public Customer getById(@PathVariable Long id) {
		return customersManager.get(id);
	}

	@PostMapping("/")
	public Customer create(@RequestBody Customer customer) {
		return customersManager.save(customer)
;
	}

	@PutMapping("/{id}")
	public Customer update(@PathVariable Long id, @RequestBody Customer customer){
		customer.setId(id);
		
		return customersManager.save(customer);
		
	}

	@DeleteMapping("/{id}")
	public Customer delete(@PathVariable Long id){
		return customersManager.delete(id);
		
	}
	
	@GetMapping("/sex")
	public List<Customer> getBySex(@RequestParam Set<Sex> sexs) {
		return customersManager.get(sexs);
	}
	
	@PutMapping("/kafka/{id}")
	public Customer putInKafka(@PathVariable Long id){
		Customer customer = getById(id);
		
		return putInKafka(customer);	
	}

	@PutMapping("/kafka/")
	public Customer putInKafka(@RequestBody Customer customer){
		logger.info("Putting custumer {} in kafka topic {}", kafkaTopic, customer);

		kafkaTemplate.send(kafkaTopic, customer);
		return customer;	
	}
	
	@PostMapping("/server2/{id}")
	public Customer saveServer2Customer(@RequestParam Long id) {
		Customer customer = getById(id);
		return server2.saveCustomer(customer);
		
	}


	@PutMapping("/server2/")
	public Customer saveServer2Customer(@RequestBody Customer customer) {
		return server2.saveCustomer(customer);
		
	}
	
	@GetMapping("/server2/{id}")
	public Customer getServer2Customer(@PathParam("id") Long id) {
		return server2.getCustomer(id);
		
	}

	@GetMapping("/server2/all")
	public List<Customer> getServer2CustomerAll() {
		return server2.getAll();
		
	}

	
}