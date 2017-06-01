package com.redpacket.server.model;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMusicMessage.Music;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage.Item;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVideoMessage.Video;
import me.chanjar.weixin.mp.builder.outxml.NewsBuilder;

@Entity
public class WechatAutoReply implements Serializable{
	
	public static enum Type {
		text, image, voice, video, news, music
	}
	
	private static final long serialVersionUID = 2639911931187343918L;

	private Long id;
	
	private String keyword;
	
	/**
	 * 类型, see WxConsts
	 */
    private Type type;
	
	/**
	 * 内容，JSON格式, see WxMpXmlOutMusicMessage/WxMpXmlOutMusicMessage/...
	 */
    private String content;
    
    /**
     * 顺序，数字小的优先级高
     */
    private int priority = 0;
    
    /**
     * 对应content转换而来的对象
     */
    private Object message;

    
	public WechatAutoReply() {
	}

	public WechatAutoReply(String keyword, Type type, String content) {
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

	@Enumerated(EnumType.STRING)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    @Transient
	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void contentDeserialize() {
		switch (type.name()) {
		case WxConsts.XML_MSG_TEXT:
			message = content;
			break;
		case WxConsts.XML_MSG_IMAGE:
			message = deserialize(MediaId.class);
			break;
		case WxConsts.XML_MSG_VOICE:
			message = deserialize(MediaId.class);
			break;
		case WxConsts.XML_MSG_VIDEO:
			message = deserialize(Video.class);
			break;
		case WxConsts.XML_MSG_NEWS:
			message = deserialize(Item[].class);
			break;
		case WxConsts.XML_MSG_MUSIC:
			message = deserialize(Music.class);
			break;
		default:
			message = null;
			break;
		}
	}
	
	public WxMpXmlOutMessage constructWxMpXmlOutMessage(String fromUser, String toUser) {
		WxMpXmlOutMessage messageToSend;
		switch (type.name()) {
		case WxConsts.XML_MSG_TEXT:
			messageToSend = WxMpXmlOutMessage.TEXT().content((String)message)
			.fromUser(fromUser).toUser(toUser).build();
			break;
		case WxConsts.XML_MSG_IMAGE:
			messageToSend = WxMpXmlOutMessage.IMAGE().mediaId(((MediaId)message).getMediaId())
			.fromUser(fromUser).toUser(toUser).build();
			break;
		case WxConsts.XML_MSG_VOICE:
			messageToSend = WxMpXmlOutMessage.VOICE().mediaId(((MediaId)message).getMediaId())
			.fromUser(fromUser).toUser(toUser).build();
			break;
		case WxConsts.XML_MSG_VIDEO:
			messageToSend = WxMpXmlOutMessage.VIDEO().mediaId(((Video)message).getMediaId())
			.title(((Video)message).getTitle())
			.description(((Video)message).getDescription())
			.fromUser(fromUser).toUser(toUser).build();
			break;
		case WxConsts.XML_MSG_NEWS:
			Item[] messageObj = (Item[]) message;
			NewsBuilder newsBuilder = WxMpXmlOutMessage.NEWS();
			for(int i = 0; i < messageObj.length; i++) {
				newsBuilder.addArticle(messageObj[i]);
			}
			messageToSend = newsBuilder.fromUser(fromUser).toUser(toUser).build();
			break;
		case WxConsts.XML_MSG_MUSIC:
			messageToSend = WxMpXmlOutMessage.MUSIC()
			.title(((Music)message).getTitle())
			.description(((Music)message).getDescription())
			.hqMusicUrl(((Music)message).getHqMusicUrl())
			.musicUrl(((Music)message).getMusicUrl())
			.thumbMediaId(((Music)message).getThumbMediaId())
			.fromUser(fromUser).toUser(toUser).build();
			break;
		default:
			messageToSend = null;
			break;
		}
		return messageToSend;
	}
	
	// https://github.com/chanjarster/weixin-java-tools/wiki/MP_%E5%90%8C%E6%AD%A5%E5%9B%9E%E5%A4%8D%E6%B6%88%E6%81%AF
    public <T> T deserialize(Class<T> valueType) {
    	ObjectMapper mapper = new ObjectMapper();
    	T object = null;
		try {
//			@SuppressWarnings("unchecked")
//			Class<T> classOfT = (Class<T>) ((ParameterizedType) getClass()
//                    .getGenericSuperclass()).getActualTypeArguments()[0];
			object = mapper.readValue(content, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return object;
    }

}
