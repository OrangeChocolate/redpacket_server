package com.redpacket.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.ForeignKey;

@Entity
public class ProductDetail {

    @Embeddable
    public static class ProductDetailPrimaryKey implements Serializable {
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
    }
	
    private ProductDetailPrimaryKey productDetailPrimaryKey;
	
	/**
	 * 产品
	 */
    private Product product;
    
    /**
     * 产品是否可启用扫码
     */
    private Boolean enable;

	public ProductDetail() {
	}

	public ProductDetail(ProductDetailPrimaryKey productDetailPrimaryKey, Product product, Boolean enable) {
		this.productDetailPrimaryKey = productDetailPrimaryKey;
		this.product = product;
		this.enable = enable;
	}


	@EmbeddedId
	public ProductDetailPrimaryKey getProductDetailPrimaryKey() {
		return productDetailPrimaryKey;
	}

	public void setProductDetailPrimaryKey(ProductDetailPrimaryKey productDetailPrimaryKey) {
		this.productDetailPrimaryKey = productDetailPrimaryKey;
	}

	@ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name="FK_PRODUCT_ID"))
	@JsonProperty(access = Access.WRITE_ONLY)
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
    
    
}
