package com.redpacket.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class RedPacket {
	
    private Long id;
	
	/**
	 * 红包接收人
	 */
    private WechatUser user;
    
    /**
     * 红包发放金额（单位分）
     */
    private int amount;
    
    /**
     * 红包发放时间
     */
    private Date createDateTime;

	public RedPacket() {
	}

	public RedPacket(int amount, Date createDateTime) {
		this.amount = amount;
		this.createDateTime = createDateTime;
	}

	public RedPacket(WechatUser user, int amount, Date createDateTime) {
		this.user = user;
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
	@JoinColumns({
	    @JoinColumn(name = "wechat_user_id", foreignKey = @ForeignKey(name="FK_WECHAT_USER_ID"), referencedColumnName = "id", insertable = false, updatable = false),
	    @JoinColumn(name = "wechat_nickname", referencedColumnName = "nickname", insertable = false, updatable = false)
	})
	@JsonProperty(access = Access.WRITE_ONLY)
	public WechatUser getUser() {
		return user;
	}

	public void setUser(WechatUser user) {
		this.user = user;
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
    
    
}
