package com.blink.springboot.model;


public class CustomerSimple {
	private Long id;
	private String names;
	private String lastNames;
	private Sex sex;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getLastNames() {
		return lastNames;
	}
	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public CustomerSimple() {}	
		

	public CustomerSimple(Customer customer) {
		this.setId(customer.getId());
		this.setNames(customer.getNames());
		this.setLastNames(customer.getLastNames());
		this.setSex(customer.getSex());
			
	}
	
}
