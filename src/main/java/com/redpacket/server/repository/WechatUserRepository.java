package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.City;
import com.redpacket.server.model.WechatUser;

public interface WechatUserRepository extends JpaRepository<WechatUser, Long> {

	public WechatUser findByOpenId(String openId);
	
	public WechatUser findByNickname(String nickname);

}
