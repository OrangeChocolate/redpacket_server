package com.redpacket.server.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class City {
	
    private Long id;
	
	/**
	 * 城市名称
	 */
    private String name;
    
    private Set<Product> products;

	public City() {
	}

	public City(String name) {
		this.name = name;
	}

	public City(String name, Set<Product> products) {
		this.name = name;
		this.products = products;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(mappedBy = "allowSellCities")
	@JsonProperty(access = Access.WRITE_ONLY)
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
    
	public String toString() {
	     return new ToStringBuilder(this).
	       append("name", name).
	       toString();
	   }
    
}
