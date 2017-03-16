package com.redpacket.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdminUser {
	
    private Long id;
	
	/**
	 * 用户名
	 */
    private String name;
	
	/**
	 * 加密后的密码
	 */
    private String encryptedPassword;

	public AdminUser() {
	}

	public AdminUser(String name, String encryptedPassword) {
		this.name = name;
		this.encryptedPassword = encryptedPassword;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
    
    
}
