package com.redpacket.server.wechat.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redpacket.server.model.WechatUser;
import com.redpacket.server.service.WechatUserService;
import com.redpacket.server.wechat.builder.TextBuilder;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class LocationHandler extends AbstractHandler {
	
	@Autowired
	private WechatUserService wechatUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
        if (wxMessage.getMsgType().equals(WxConsts.XML_MSG_LOCATION)) {
            //TODO 接收处理用户发送的地理位置消息
            try {
                String content = "感谢反馈，您的的地理位置已收到！";
                return new TextBuilder().build(content, wxMessage, null);
            } catch (Exception e) {
                logger.error("位置消息接收处理失败", e);
                return null;
            }
        }

        // 将用户地理位置信息保存到本地数据库，以便以后使用
        String openId = wxMessage.getFromUser();
        logger.info("收到来自{}的更新地理位置信息", openId);
        WechatUser wechatUser = wechatUserService.findByOpenId(openId);
        if(wechatUser != null) {
            logger.info("更新{}数据库中的地理位置信息", openId);
        	wechatUser.setLatitude(wxMessage.getLatitude());
        	wechatUser.setLongitude(wxMessage.getLongitude());
        	// 可以根据经纬度获取用户的真实位置信息
        	wechatUser.setUpdateDate(new Date());
        	wechatUserService.saveOrUpdate(wechatUser);
        }
        
        return null;
    }

}
