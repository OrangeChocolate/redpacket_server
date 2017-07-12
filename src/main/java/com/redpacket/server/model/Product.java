package com.redpacket.server.model;

import java.io.Serializable;
import java.util.Date;
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
	
	/**
	 * 是否随机红包
	 */
	private boolean isRandomRedpacket = false;
	
	/**
	 * 随机红包最小值
	 */
	private int randomMinAmount = 1;
	
	/**
	 * 随机红包最大值
	 */
	private int randomMaxAmount = 1000;
	
	/**
	 * 平均红包金额，当isRandomRedpacket为false时才生效
	 */
	private int averageAmount = 0;

	/**
	 * 产品详情，即对应这批产品中的所有产品项
	 */
	private Set<ProductDetail> productDetails;
	

	/**
	 * 可销售城市
	 */
	private Set<City> allowSellCities;
	
	/**
	 * 是否强制城市检查
	 */
	private boolean forceCityCheck = false;
	
	/**
	 * 产品层面的是否可以领取红包
	 */
	private boolean enable = true;
	
	/**
	 * 创建产品的时间
	 */
	private Date createDate;
    
	/**
	 * 修改产品的时间
	 */
    private Date updateDate;

	public Product() {
	}

	public Product(String name, int amount, String description, Set<City> allowSellCity) {
		this.name = name;
		this.amount = amount;
		this.description = description;
		this.allowSellCities = allowSellCity;
		this.createDate = new Date();
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
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "product_city", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_PRODUCT_ID")), inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_CITY_ID")))
	public Set<City> getAllowSellCities() {
		return allowSellCities;
	}

	public void setAllowSellCities(Set<City> allowSellCities) {
		this.allowSellCities = allowSellCities;
	}

	@OneToMany(mappedBy = "product" , cascade = CascadeType.MERGE)
	public Set<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Set<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}

	@Column(name="random_redpacket", nullable = false, columnDefinition = "TINYINT default 0", length = 1)
	public boolean isRandomRedpacket() {
		return isRandomRedpacket;
	}

	public void setRandomRedpacket(boolean isRandomRedpacket) {
		this.isRandomRedpacket = isRandomRedpacket;
	}

	@Column(nullable = false, columnDefinition = "integer default 1")
	public int getRandomMinAmount() {
		return randomMinAmount;
	}

	public void setRandomMinAmount(int randomMinAmount) {
		this.randomMinAmount = randomMinAmount;
	}

	@Column(nullable = false, columnDefinition = "integer default 1000")
	public int getRandomMaxAmount() {
		return randomMaxAmount;
	}

	public void setRandomMaxAmount(int randomMaxAmount) {
		this.randomMaxAmount = randomMaxAmount;
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

	@Column(nullable = false, columnDefinition = "TINYINT default 0", length = 1)
	public boolean isForceCityCheck() {
		return forceCityCheck;
	}

	public void setForceCityCheck(boolean forceCityCheck) {
		this.forceCityCheck = forceCityCheck;
	}

	@Column(nullable = false, columnDefinition = "TINYINT default 1", length = 1)
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ToStringBuilder.html
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
