package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.WechatAutoReply;

public interface WechatAutoReplyRepository extends JpaRepository<WechatAutoReply, Long> {

}
