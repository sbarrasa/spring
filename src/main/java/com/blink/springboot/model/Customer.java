package com.blink.springboot.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "customers")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private LocalDate birthday;
	private String names;
	@Column(name = "lastnames")
	private String lastNames;
	private Sex sex;
	@Type(type = "jsonb")
    @Column(columnDefinition = "json") 
	private List<Specs> specs;
	@Transient
	private List<Customer> childs;


	public Customer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getNames() {
		return names;
	}

	public Customer setNames(String names) {
		this.names = names;
		return this;
	}



	public Integer getAge() {
		return Period.between(birthday, LocalDate.now()).getYears();
	}

	public Sex getSex() {
		return sex;
	}

	public Customer setSex(Sex sex) {
		this.sex = sex;
		return this;
	}

	public List<Customer> getChilds() {
		return childs;
	}

	public void setChilds(List<Customer> childs) {
		this.childs = childs;
	}

	public List<Specs> getSpecs() {
		return this.specs;
	}


	public void setSpecs(List<Specs> specs) {
		this.specs = specs;
	}

	public String getLastNames() {
		return lastNames;
	}

	public Customer setLastNames(String lastNames) {
		this.lastNames = lastNames;
		return this;
	}

}
