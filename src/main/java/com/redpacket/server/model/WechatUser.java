package com.redpacket.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
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
    private String headimgurl;
    
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

	public WechatUser() {
	}

	public WechatUser(String openId, String nickname, String sex, String city, String country, String province,
			String language, String headimgurl) {
		this.openId = openId;
		this.nickname = nickname;
		this.sex = sex;
		this.city = city;
		this.country = country;
		this.province = province;
		this.language = language;
		this.headimgurl = headimgurl;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
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
    
    
}
