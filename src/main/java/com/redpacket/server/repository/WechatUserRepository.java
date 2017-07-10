package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.redpacket.server.model.WechatUser;

public interface WechatUserRepository extends JpaRepository<WechatUser, Long>, JpaSpecificationExecutor<WechatUser> {

	public WechatUser findByOpenId(String openId);
	
	public WechatUser findByNickname(String nickname);

}
