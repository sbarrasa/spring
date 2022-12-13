package com.blink.springboot.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;



public class HystrixConfig {
	@Bean
    @Primary
    @Order(value=Ordered.HIGHEST_PRECEDENCE) 
	public HystrixCommandAspect hystrixAspect() {
	     return new HystrixCommandAspect();
	 }
	 
	 
	
}
