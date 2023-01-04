package com.blink.springboot.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties(prefix = "com.blink.demoservice")
public class DemoService {
	private String name;
	private Long id;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public DemoService setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return this.name;
	}
	
	public Long getId() {
		return id;
	}

	public DemoService setId(Long id) {
		this.id = id;
		return this;
	}
	 
	public String getApplicationName() {
		return applicationContext.getId();
	}
	
	public List<String> getBeans(){
		return Arrays.stream(applicationContext.getBeanDefinitionNames())
				.filter(beanName -> applicationContext.getBean(beanName).getClass().getName().startsWith("com.blink"))
				.collect(Collectors.toList());
	}
	
	public String toString() {
		return "#%d: %s".formatted(id, name);
	}
}
