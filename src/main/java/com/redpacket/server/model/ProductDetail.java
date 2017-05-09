package com.redpacket.server.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
public class ProductDetail implements Comparable<ProductDetail>, Serializable {

	private static final long serialVersionUID = -3725950221569975053L;

	private Long id;

    /**
     * 产品ID
     */
	private Long productId;

	/**
	 * 产品详情序号
	 */
    private Long productDetailNum;
	
	/**
	 * 产品
	 */
    private Product product;
    
    /**
     * 产品是否可启用扫码
     */
    private boolean enable = true;
    
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 是否已扫码
     */
    private boolean isScanned = false;
    
    private RedPacket redPacket;

	public ProductDetail() {
	}

	public ProductDetail(Product product, Long productDetailNum) {
		this.product = product;
		this.productId = product.getId();
		this.productName = product.getName();
		this.productDetailNum = productDetailNum;
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "product_id")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

    
	public Long getProductDetailNum() {
		return productDetailNum;
	}

	public void setProductDetailNum(Long productDetailNum) {
		this.productDetailNum = productDetailNum;
	}

	@ManyToOne
	@JoinColumns(foreignKey = @ForeignKey(name = "FK_PRODUCT_COMPOSITE_KEY"), value = {
			@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false),
			@JoinColumn(name = "product_name", referencedColumnName = "name", insertable = false, updatable = false) })
	@JsonProperty(access = Access.WRITE_ONLY)
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(nullable = false, columnDefinition = "TINYINT default 1", length = 1)
	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

    @Column(name="product_name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(nullable = false, columnDefinition = "TINYINT default 0", length = 1)
	public boolean isScanned() {
		return isScanned;
	}

	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}

	@OneToOne(mappedBy="productDetail")
//	@OneToOne
//	@JoinColumn(name="red_packet_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	public RedPacket getRedPacket() {
		return redPacket;
	}

	public void setRedPacket(RedPacket redPacket) {
		this.redPacket = redPacket;
	}


	/**
	 * 产品详情的联合主键，使用产品ID和产品详情序号，
	 * 这里使用@IdClass的方式，而不是@Embeddable、@EmbeddedId避免嵌套属性
	 * @see http://stackoverflow.com/questions/29952386/embedded-id-and-repeated-column-in-mapping-for-entity-exception
	 * @see http://stackoverflow.com/questions/4432748/what-does-attributeoverride-mean
	 * @author Liu.D.H
	 */
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + (isScanned ? 1231 : 1237);
		result = prime * result + ((productDetailNum == null) ? 0 : productDetailNum.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDetail other = (ProductDetail) obj;
		if (enable != other.enable)
			return false;
		if (isScanned != other.isScanned)
			return false;
		if (productDetailNum == null) {
			if (other.productDetailNum != null)
				return false;
		} else if (!productDetailNum.equals(other.productDetailNum))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProductDetail other) {
		if(this.productId != other.productId) {
			return (int) (this.productId - other.productId);
		}
		return (int) (this.productDetailNum - other.productDetailNum);
	}
	
	
}
