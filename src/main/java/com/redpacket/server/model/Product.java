package com.redpacket.server.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Product {

	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 数量
	 */
	private int amount;

	/**
	 * 描述
	 */
	private String description;

	private Set<ProductDetail> productDetails;

	/**
	 * 可销售城市
	 */
	private Set<City> allowSellCities;

	public Product() {
	}

	public Product(String name, int amount, String description) {
		this.name = name;
		this.amount = amount;
		this.description = description;
	}

	public Product(String name, int amount, String description, Set<City> allowSellCity) {
		this.name = name;
		this.amount = amount;
		this.description = description;
		this.allowSellCities = allowSellCity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// 定义Product-City的ManyToMany关系，可参考https://hellokoding.com/jpa-many-to-many-relationship-mapping-example-with-spring-boot-hsql/
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "product_city", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
	public Set<City> getAllowSellCities() {
		return allowSellCities;
	}

	public void setAllowSellCities(Set<City> allowSellCities) {
		this.allowSellCities = allowSellCities;
	}

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	public Set<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Set<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}

	// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ToStringBuilder.html
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
