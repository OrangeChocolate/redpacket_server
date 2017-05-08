package com.redpacket.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.redpacket.server.model.ProductDetail.ProductDetailPrimaryKey;

@Entity
@IdClass(ProductDetailPrimaryKey.class)
public class ProductDetail implements Serializable {

	private static final long serialVersionUID = -3725950221569975053L;

    /**
     * 产品ID
     */
	private Long productId;

	/**
	 * 产品详情序号
	 */
    private Long productDetailId;
	
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

	public ProductDetail() {
	}

	public ProductDetail(Product product, Boolean enable) {
		this.product = product;
		this.productId = product.getId();
		this.enable = enable;
	}
    
    @Id
	@Column(name = "product_id")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

    @Id
    @Column(name = "product_detail_id")
	public Long getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(Long productDetailId) {
		this.productDetailId = productDetailId;
	}
    
	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name="FK_PRODUCT_ID"), referencedColumnName = "id", insertable = false, updatable = false),
	    @JoinColumn(name = "product_name", referencedColumnName = "name", insertable = false, updatable = false)
	})
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


	/**
	 * 产品详情的联合主键，使用产品ID和产品详情序号，
	 * 这里使用@IdClass的方式，而不是@Embeddable、@EmbeddedId避免嵌套属性
	 * @see http://stackoverflow.com/questions/29952386/embedded-id-and-repeated-column-in-mapping-for-entity-exception
	 * @see http://stackoverflow.com/questions/4432748/what-does-attributeoverride-mean
	 * @author Liu.D.H
	 *
	 */
    public static class ProductDetailPrimaryKey implements Serializable {

		private static final long serialVersionUID = 8860150264004330941L;

		private Long productId;

        private Long productDetailId;
        
        public ProductDetailPrimaryKey() {
		}

		public ProductDetailPrimaryKey(Long productId, Long productDetailId) {
			this.productId = productId;
			this.productDetailId = productDetailId;
		}

		@Column(name = "product_id", nullable = false, updatable = false)
		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

        @Column(name = "product_detail_id", nullable = false, updatable = false)
		public Long getProductDetailId() {
			return productDetailId;
		}

		public void setProductDetailId(Long productDetailId) {
			this.productDetailId = productDetailId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((productDetailId == null) ? 0 : productDetailId.hashCode());
			result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
			ProductDetailPrimaryKey other = (ProductDetailPrimaryKey) obj;
			if (productDetailId == null) {
				if (other.productDetailId != null)
					return false;
			} else if (!productDetailId.equals(other.productDetailId))
				return false;
			if (productId == null) {
				if (other.productId != null)
					return false;
			} else if (!productId.equals(other.productId))
				return false;
			return true;
		}
    }
}
