package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.RedPacket;
import com.redpacket.server.repository.RedPacketRepository;

@Service
public class RedPacketService {
	
	@Autowired
	private RedPacketRepository redPacketRepository;
	
	public List<RedPacket> findAll() {
		return redPacketRepository.findAll();
	}
	
	public RedPacket findById(long id) {
		return redPacketRepository.findOne(id);
	}

	public RedPacket saveOrUpdate(RedPacket redPacket) {
		return redPacketRepository.save(redPacket);
	}

	public void delete(RedPacket redPacket) {
		redPacketRepository.delete(redPacket);
	}

}
