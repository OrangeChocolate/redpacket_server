package com.redpacket.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.redpacket.server.common.Utils;
import com.redpacket.server.model.RedPacket;
import com.redpacket.server.repository.RedPacketRepository;

@Service
public class RedPacketService {
	
	@Autowired
	private RedPacketRepository redPacketRepository;
	
	public List<RedPacket> findAll() {
		return redPacketRepository.findAll();
	}

	public Page<RedPacket> findAll(Specification<RedPacket> spec, Pageable pageable) {
		return redPacketRepository.findAll(spec, pageable);
	}
	
	public RedPacket findById(long id) {
		return redPacketRepository.findOne(id);
	}
	
	public List<RedPacket> findByUserOpenId(String openId) {
		return redPacketRepository.findByUserOpenId(openId);
	}
	
	public List<RedPacket> findByUserOpenIdCurrentDay(String openId, String dateString) {
		Date dayBegin = Utils.getDateBegin(dateString);
		Date dayEnd = Utils.getDateEnd(dateString);
		return redPacketRepository.findByUserOpenIdAndCreateDateTimeBetween(openId, dayBegin, dayEnd);
	}
	
	public List<RedPacket> findByUserOpenIdCurrentDay(String openId, Date currentDate) {
		Date dayBegin = Utils.getDateBegin(currentDate);
		Date dayEnd = Utils.getDateEnd(currentDate);
		return redPacketRepository.findByUserOpenIdAndCreateDateTimeBetween(openId, dayBegin, dayEnd);
	}
	
	public List<RedPacket> findByProductDetailId(long productDetailId) {
		return redPacketRepository.findByProductDetailId(productDetailId);
	}

	public RedPacket saveOrUpdate(RedPacket redPacket) {
		return redPacketRepository.save(redPacket);
	}

	public void delete(RedPacket redPacket) {
		redPacketRepository.delete(redPacket);
	}

}
