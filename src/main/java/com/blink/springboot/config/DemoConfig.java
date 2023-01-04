package com.blink.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blink.springboot.services.DemoService;

//@Configuration
public class DemoConfig {
	@Autowired()
	private DemoService demo;
	
	@Bean(name = "demo")
	@ConfigurationProperties(prefix = "com.blink.springboot.demoservice")
	public DemoService getDemoSingleton() {
		return demo;
	}
	
	@Bean(name = "demoDefault")
	@ConfigurationProperties(prefix = "com.blink.springboot.demoservice")
	public DemoService getNewDemo() {
		return new DemoService();
	}
	
	

}
