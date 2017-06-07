package com.redpacket.server.wechat.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.service.ExtraFeatureService;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Donghua Liu
 *
 */
@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class LocationHandler extends AbstractHandler {
	
	@Autowired
	ExtraFeatureService extraFeatureService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
    	// 处理用户发送的地理位置信息，默认不处理
    	/*
		WxMpXmlMessage[
		  toUser=gh_ee82263bd74b
		  fromUser=oseIo1mUcV1BTM550N3H0QmxeSWg
		  createTime=1496800724
		  msgType=location
		  msgId=6428710158719728710
		  locationX=24.831759
		  locationY=102.847603
		  scale=15.0
		  label=昆明市呈贡区云南大学(呈贡校区)
		]
    	 */
        if (wxMessage.getMsgType().equals(WxConsts.XML_MSG_LOCATION)) {
//            String content = "感谢反馈，您的的地理位置已收到！";
//            return new TextBuilder().build(content, wxMessage, null);
            return null;
        }

        // 处理msgType=event/event=LOCATION的地理位置信息
    	/*
		WxMpXmlMessage[
		  toUser=gh_ee82263bd74b
		  fromUser=oseIo1mUcV1BTM550N3H0QmxeSWg
		  createTime=1496800621
		  msgType=event
		  event=LOCATION
		  latitude=24.835211
		  longitude=102.846352
		  precision=100.0
		]
    	 */
        // 更新用户的地理位置信息
        extraFeatureService.updateLocation(wxMessage.getFromUser(), wxMessage.getLatitude(), wxMessage.getLongitude());
        
        return null;
    }

}
