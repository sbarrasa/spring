package com.blink.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Application {
	private static ObjectMapper objectMapper;
	
	public static ObjectMapper objectMapper() {
		if(objectMapper == null)
			objectMapper = new ObjectMapper();
		return objectMapper;
	}
	
	public static void main(String[] args) {
	
		SpringApplication.run(Application.class, args);
		
	}


}