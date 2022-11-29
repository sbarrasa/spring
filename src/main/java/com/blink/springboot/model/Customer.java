package com.blink.springboot.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String names;
	@Column(name = "lastnames")
	private String lastNames;
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	private LocalDate birthday;
	@Type(type = "jsonb")
    @Column(columnDefinition = "json") 
	private List<Specs> specs;
	@ElementCollection
	@CollectionTable(name="childs")
	private List<Customer> childs;
	

	public Customer() {}

	
	public Customer(Long id) {	
		this.id = id;
	}

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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}




	public Integer getAge() {
		return Period.between(birthday, LocalDate.now()).getYears();
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

	

}
