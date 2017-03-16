package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.Product;
import com.redpacket.server.model.RedPacket;

public interface RedPacketRepository extends JpaRepository<RedPacket, Long> {

}
