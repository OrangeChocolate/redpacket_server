package com.redpacket.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(name = "UK_WECHAT_USER_ID_NICKNAME", columnNames = { "id", "nickname" }))
public class WechatUser implements Serializable {
	
	private static final long serialVersionUID = 5416290197708277177L;

	private Long id;
	
	// see http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
	/**
	 * 微信用户的openId
	 */
	private String openId;
	
	/**
	 * 微信用户的昵称
	 */
    private String nickname;
	
	/**
	 * 微信用户的性别
	 */
    private String sex;
	
	/**
	 * 微信用户所在城市
	 */
    private String city;
	
	/**
	 * 微信用户所在国家
	 */
    private String country;
	
	/**
	 * 微信用户所在省份
	 */
    private String province;
	
	/**
	 * 微信用户所用语言
	 */
    private String language;
	
	/**
	 * 微信用户的头像url
	 */
    private String headImgUrl;
    
    /**
     * 微信用户地理位置经度
     */
    private double longitude;
    
    /**
     * 微信用户地理位置纬度
     */
    private double latitude;
    
    /**
     * 根据地理位置计算的实际城市
     */
    private String actualCity;
    
    /**
     * 微信用户红包记录
     */
    private Set<RedPacket> redPackets;
    
    private Date createDate;
    
    private Date updateDate;
    
    private boolean subscribe = true;

	public WechatUser() {
	}

	public WechatUser(String openId, String nickname, String sex, String city, String country, String province,
			String language, String headImgUrl) {
		this.openId = openId;
		this.nickname = nickname;
		this.sex = sex;
		this.city = city;
		this.country = country;
		this.province = province;
		this.language = language;
		this.headImgUrl = headImgUrl;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 100)
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(nullable = false, length = 100)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getActualCity() {
		return actualCity;
	}

	public void setActualCity(String actualCity) {
		this.actualCity = actualCity;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<RedPacket> getRedPackets() {
		return redPackets;
	}

	public void setRedPackets(Set<RedPacket> redPackets) {
		this.redPackets = redPackets;
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

	@Column(nullable = false, columnDefinition = "TINYINT default 1", length = 1)
	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}
    
    
}
