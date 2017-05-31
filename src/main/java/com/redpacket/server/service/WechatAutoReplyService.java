package com.redpacket.server.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.WechatAutoReply;
import com.redpacket.server.repository.WechatAutoReplyRepository;

@Service
public class WechatAutoReplyService {

	@Autowired
	private WechatAutoReplyRepository wechatAutoReplyRepository;

	private Map<String, WechatAutoReply> cachedMapReplies;

	public List<WechatAutoReply> findAll() {
		return wechatAutoReplyRepository.findAllByOrderByPriorityAsc();
	}

	public void updateAutoReply() {
		Map<String, WechatAutoReply> allMappedReplies = new TreeMap<>();
		List<WechatAutoReply> allReplies = wechatAutoReplyRepository.findAllByOrderByPriorityAsc();
		allReplies.stream().forEach(autoReply -> {
			autoReply.contentDeserialize();
			allMappedReplies.put(autoReply.getKeyword(), autoReply);
		});
		this.cachedMapReplies = allMappedReplies;
	}

	public WechatAutoReply findById(long id) {
		return wechatAutoReplyRepository.findOne(id);
	}

	public WechatAutoReply save(WechatAutoReply wechatAutoReply) {
		WechatAutoReply savedAutoReply = wechatAutoReplyRepository.save(wechatAutoReply);
		updateAutoReply();
		return savedAutoReply;
	}

	public void delete(long id) {
		wechatAutoReplyRepository.delete(id);
		updateAutoReply();
	}

	public Map<String, WechatAutoReply> getAllMappedReplies() {
		if(cachedMapReplies == null || cachedMapReplies.size() == 0) {
			updateAutoReply();
		}
		return cachedMapReplies;
	}

}
