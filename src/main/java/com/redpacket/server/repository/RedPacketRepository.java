package com.redpacket.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.redpacket.server.model.RedPacket;

public interface RedPacketRepository extends JpaRepository<RedPacket, Long>, JpaSpecificationExecutor<RedPacket> {

	List<RedPacket> findByUserOpenId(String openId);

	List<RedPacket> findByUserOpenIdAndCreateDateTimeBetween(String openId, Date dayBegin, Date dayEnd);

}
