package com.redpacket.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.redpacket.server.model.WechatUser;
import com.redpacket.server.repository.WechatUserRepository;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

@Service
public class WechatUserService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WechatUserRepository wechatUserRepository;

    @Autowired
    private WxMpService wxService;
	
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
	
	// https://mp.weixin.qq.com/wiki?id=mp1421140840&t=0.002122875695626636
	// https://github.com/Wechat-Group/weixin-java-tools/wiki/MP_%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86
	public void initializationWeChatUsers() {
		List<String> allOpenIds = new ArrayList<>();
		try {
			WxMpUserList wxUserList = wxService.getUserService().userList(null);
			List<String> openids = wxUserList.getOpenids();
			allOpenIds.addAll(openids);
			String nextOpenid = wxUserList.getNextOpenid();
			while (!StringUtils.isEmpty(nextOpenid)) {
				wxUserList = wxService.getUserService().userList(nextOpenid);
				openids = wxUserList.getOpenids();
				allOpenIds.addAll(openids);
				nextOpenid = wxUserList.getNextOpenid();
			}
			// 获取详细信息
			int allWxUserSize = allOpenIds.size();
			for(int i = 0; i< allWxUserSize; i++) {
				String openId = allOpenIds.get(i);
				WxMpUser userWxInfo = wxService.getUserService().userInfo(openId, "zh_CN");
				WechatUser wechatUser = wechatUserRepository.findByOpenId(openId);
				if(wechatUser == null) {
					wechatUser = WechatUser.convertWxUserInfoToWechatUser(userWxInfo);
				}
				else {
					WechatUser.updateWechatUserFromWxUserInfo(wechatUser, userWxInfo);
				}
				logger.info("update wechatUser: {}, percentage: {}%", wechatUser.getNickname(), i * 1.0 / allWxUserSize * 100);
				wechatUserRepository.save(wechatUser);
			}
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
