package com.redpacket.server.wechat.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redpacket.server.model.WechatUser;
import com.redpacket.server.service.WechatUserService;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Donghua Liu
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

	@Autowired
	private WechatUserService wechatUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        logger.info("取消关注用户 OPENID: " + openId);
        WechatUser wechatUser = wechatUserService.findByOpenId(openId);
        if(wechatUser != null) {
	        wechatUser.setSubscribe(false);
	        wechatUser.setUpdateDate(new Date());
	        wechatUserService.saveOrUpdate(wechatUser);
        }
        return null;
    }

}
