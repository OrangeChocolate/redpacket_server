package com.redpacket.server.model;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMusicMessage.Music;

@Entity
public class WechatAutoReply implements Serializable{
	
	private static final long serialVersionUID = 2639911931187343918L;

	private Long id;
	
	private String keyword;
	
	/**
	 * 类型, see WxConsts
	 */
    private String type;
	
	/**
	 * 内容，JSON格式, see WxMpXmlOutMusicMessage/WxMpXmlOutMusicMessage/...
	 */
    private String content;

    
	public WechatAutoReply() {
	}

	public WechatAutoReply(String keyword, String type, String content) {
		this.keyword = keyword;
		this.type = type;
		this.content = content;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
	// https://github.com/chanjarster/weixin-java-tools/wiki/MP_%E5%90%8C%E6%AD%A5%E5%9B%9E%E5%A4%8D%E6%B6%88%E6%81%AF
    public <T> T deserialize() {
    	ObjectMapper mapper = new ObjectMapper();
    	T object = null;
		try {
			@SuppressWarnings("unchecked")
			Class<T> classOfT = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
			object = mapper.readValue(content, classOfT);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return object;
    }

}
