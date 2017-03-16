package com.redpacket.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductDetail {
	
    private Long id;
	
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

	public ProductDetail(Long id, Product product, Boolean enable) {
		this.id = id;
		this.product = product;
		this.enable = enable;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
    @JoinColumn(name = "product_id")
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
