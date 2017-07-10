package com.redpacket.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.redpacket.server.model.WechatAutoReply;

public interface WechatAutoReplyRepository extends JpaRepository<WechatAutoReply, Long>, JpaSpecificationExecutor<WechatAutoReply> {

	List<WechatAutoReply> findAllByOrderByPriorityAsc();
}
