package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.WechatUser;
import com.redpacket.server.repository.WechatUserRepository;

@Service
public class WechatUserService {
	
	@Autowired
	private WechatUserRepository wechatUserRepository;
	
	public List<WechatUser> findAll() {
		return wechatUserRepository.findAll();
	}
	
	public WechatUser findById(long id) {
		return wechatUserRepository.findOne(id);
	}

	public WechatUser saveOrUpdate(WechatUser wechatUser) {
		return wechatUserRepository.save(wechatUser);
	}

	public void delete(WechatUser wechatUser) {
		wechatUserRepository.delete(wechatUser);
	}

	public WechatUser findByOpenId(String openId) {
		return wechatUserRepository.findByOpenId(openId);
	}

}
