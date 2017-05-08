package com.redpacket.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UK_PRODUCT_ID_NAME", columnNames = { "id", "name" }))
public class Product implements Serializable {

	private static final long serialVersionUID = 6804853862173665407L;

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
	
	private boolean isRandomRedpacket = false;
	
	private int averageAmount = 0;

	private Set<ProductDetail> productDetails;
	
//	private Set<RedPacket> redPackets;

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
	@JoinTable(name = "product_city", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_PRODUCT_ID")), inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_CITY_ID")))
	public Set<City> getAllowSellCities() {
		return allowSellCities;
	}

	public void setAllowSellCities(Set<City> allowSellCities) {
		this.allowSellCities = allowSellCities;
	}

	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	public Set<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Set<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}

	@Column(name="random_redpacket")
	public boolean isRandomRedpacket() {
		return isRandomRedpacket;
	}

	public void setRandomRedpacket(boolean isRandomRedpacket) {
		this.isRandomRedpacket = isRandomRedpacket;
	}
	
	@Column(name="average_amount")
	public int getAverageAmount() {
		return averageAmount;
	}

	public void setAverageAmount(int averageAmount) {
		this.averageAmount = averageAmount;
	}
	
//	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//	public Set<RedPacket> getRedPackets() {
//		return redPackets;
//	}
//
//	public void setRedPackets(Set<RedPacket> redPackets) {
//		this.redPackets = redPackets;
//	}

	// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ToStringBuilder.html
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
