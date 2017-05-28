package com.redpacket.server.wechat.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redpacket.server.model.WechatUser;
import com.redpacket.server.service.WechatUserService;
import com.redpacket.server.wechat.builder.TextBuilder;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	
	@Autowired
	private WechatUserService wechatUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService()
            .userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            // 添加关注用户到本地
        	WechatUser wechatUser = wechatUserService.findByOpenId(userWxInfo.getOpenId());
        	// 之前已经关注过，现在重新关注
        	if(wechatUser != null) {
        		wechatUser.setNickname(userWxInfo.getNickname());
        		wechatUser.setHeadImgUrl(userWxInfo.getHeadImgUrl());
        		wechatUser.setCity(userWxInfo.getCity());
        		wechatUser.setProvince(userWxInfo.getProvince());
        		wechatUser.setCountry(userWxInfo.getCountry());
        		wechatUser.setUpdateDate(new Date());
        	}
        	// 首次关注
        	else {
        		wechatUser = new WechatUser(userWxInfo.getOpenId(), userWxInfo.getNickname(), userWxInfo.getSex(), userWxInfo.getCity(),
        				userWxInfo.getCountry(), userWxInfo.getProvince(), userWxInfo.getLanguage(), userWxInfo.getHeadImgUrl());
        		Date currentDate = new Date();
        		wechatUser.setCreateDate(currentDate);
        		wechatUser.setUpdateDate(currentDate);
        	}
        	wechatUserService.saveOrUpdate(wechatUser);
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }

}
