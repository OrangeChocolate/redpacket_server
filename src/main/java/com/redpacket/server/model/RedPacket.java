package com.redpacket.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class RedPacket implements Serializable {
	
	private static final long serialVersionUID = 7567567480558510660L;

	private Long id;
	
	/**
	 * 红包接收人
	 */
    private WechatUser user;
	
	private Long wechatUserId;
	private String wechatNickname;
	private String wechatOpenId;
    
    /**
     * 红包发放金额（单位分）
     */
    private int amount = 0;
    
    /**
     * 红包发放时间
     */
    private Date createDateTime = new Date();
    
    private ProductDetail productDetail;
    
    private long productDetailId;
    private String productDetailProductName;
	
	public RedPacket() {
	}

	public RedPacket(int amount, Date createDateTime) {
		this.amount = amount;
		this.createDateTime = createDateTime;
	}

	public RedPacket(WechatUser user, ProductDetail productDetail, int amount, Date createDateTime) {
		this.user = user;
		this.wechatUserId = user.getId();
		this.wechatNickname = user.getNickname();
		this.wechatOpenId = user.getOpenId();
		this.productDetail = productDetail;
		this.productDetailId = productDetail.getId();
		this.productDetailProductName = productDetail.getProductName();
		this.amount = amount;
		this.createDateTime = createDateTime;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumns(foreignKey = @ForeignKey(name = "FK_WECHAT_USER_COMPOSITE"), value = {
	    @JoinColumn(name = "wechat_user_id", foreignKey = @ForeignKey(name="FK_WECHAT_USER_ID"), referencedColumnName = "id", insertable = false, updatable = false),
	    @JoinColumn(name = "wechat_nickname", referencedColumnName = "nickname", insertable = false, updatable = false),
	    @JoinColumn(name = "wechat_open_id", referencedColumnName = "openId", insertable = false, updatable = false)
	})
	@JsonProperty(access = Access.WRITE_ONLY)
	public WechatUser getUser() {
		return user;
	}

	public void setUser(WechatUser user) {
		this.user = user;
	}

    @Column(name="wechat_user_id")
	public Long getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(Long wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

    @Column(name="wechat_nickname", length=100)
	public String getWechatNickname() {
		return wechatNickname;
	}

	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}

    @Column(name="wechat_open_id", length=100)
	public String getWechatOpenId() {
		return wechatOpenId;
	}

	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

//	@ManyToOne
//	@JoinColumns(foreignKey = @ForeignKey(name = "FK_PRODUCT_COMPOSITE"), value = {
//	    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false),
//	    @JoinColumn(name = "product_name", referencedColumnName = "name", insertable = false, updatable = false)
//	})
//	@JsonProperty(access = Access.WRITE_ONLY)
//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
	
	@OneToOne
	@PrimaryKeyJoinColumns(foreignKey = @ForeignKey(name = "FK_PRODUCT_DETAIL_COMPOSITE"), value = {
			@PrimaryKeyJoinColumn(name = "product_detail_id", referencedColumnName = "id"),
			@PrimaryKeyJoinColumn(name = "product_detail_product_name", referencedColumnName = "product_name")})
	@JsonProperty(access = Access.WRITE_ONLY)
    public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

//	// Table [red_packet] contains physical column name [product_detail_id] referred to by multiple physical column names: [product_detail_id], [productDetailId]
	// https://stackoverflow.com/questions/16335513/hibernate-find-with-composite-key-invalid-column-name-exception
	@PrimaryKeyJoinColumn(name = "product_detail_id")
	public long getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(long productDetailId) {
		this.productDetailId = productDetailId;
	}

	@PrimaryKeyJoinColumn(name="product_detail_product_name")
	public String getProductDetailProductName() {
		return productDetailProductName;
	}

	public void setProductDetailProductName(String productDetailProductName) {
		this.productDetailProductName = productDetailProductName;
	}

}
