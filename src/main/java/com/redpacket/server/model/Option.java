package com.redpacket.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Option {
	
    private Long id;
	
	/**
	 * 配置项名称
	 */
    private String name;
	
	/**
	 * 配置项值
	 */
    private String value;
    
    /**
     * 配置项是否启用
     */
    private Boolean enable;

	public Option() {
	}

	public Option(Long id, String name, String value, Boolean enable) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.enable = enable;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
    
    
}
