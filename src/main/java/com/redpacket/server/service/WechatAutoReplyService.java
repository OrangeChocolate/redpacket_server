package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.WechatAutoReply;
import com.redpacket.server.repository.WechatAutoReplyRepository;

@Service
public class WechatAutoReplyService {
	
	@Autowired
	private WechatAutoReplyRepository WechatAutoReplyRepository;
	
	public List<WechatAutoReply> findAll() {
		return WechatAutoReplyRepository.findAll();
	}
	
	public WechatAutoReply findById(long id) {
		return WechatAutoReplyRepository.findOne(id);
	}
	
	public WechatAutoReply save(WechatAutoReply wechatAutoReply) {
		return WechatAutoReplyRepository.save(wechatAutoReply);
	}
	
	public void delete(long id) {
		WechatAutoReplyRepository.delete(id);
	}

}
