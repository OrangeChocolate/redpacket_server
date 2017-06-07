package com.redpacket.server.wechat.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redpacket.server.ApplicationMessageConfiguration;
import com.redpacket.server.model.WechatAutoReply;
import com.redpacket.server.service.WechatAutoReplyService;
import com.redpacket.server.wechat.builder.TextBuilder;
import com.redpacket.server.wechat.utils.JsonUtils;

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
public class MsgHandler extends AbstractHandler {

	public static final Logger logger = LoggerFactory.getLogger(MsgHandler.class);
	
	@Autowired
	private WechatAutoReplyService wechatAutoReplyService;
	
	@Autowired
	private ApplicationMessageConfiguration applicationMessageConfiguration;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager)    {

//        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
//        try {
//            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
//                && weixinService.getKefuService().kfOnlineList()
//                    .getKfOnlineList().size() > 0) {
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                    .fromUser(wxMessage.getToUser())
//                    .toUser(wxMessage.getFromUser()).build();
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }

        // 日志记录收到的消息
        logger.info("received message：" + JsonUtils.toJson(wxMessage));

        // 只处理文本的自定义回复内容
        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_TEXT)) {
        	return new TextBuilder().build(applicationMessageConfiguration.autoReplyNotSupportThisType,
        			wxMessage, weixinService);
        }
        
		String receivedContent = wxMessage.getContent();
		Map<String, WechatAutoReply> allMappedReplies = wechatAutoReplyService.getAllMappedReplies();
		WechatAutoReply wechatAutoReply = null;
		for (Entry<String, WechatAutoReply> entry : allMappedReplies.entrySet()) {
			String keywordString = entry.getKey();
			WechatAutoReply reply = entry.getValue();
			if (receivedContent.contains(keywordString)) {
				wechatAutoReply = reply;
				break;
			}
		}
		if (wechatAutoReply == null) {
			return new TextBuilder().build(applicationMessageConfiguration.autoReplyNotSupportYourMessage, wxMessage,
					weixinService);
		}
		return wechatAutoReply.constructWxMpXmlOutMessage(wxMessage.getToUser(), wxMessage.getFromUser());
        

    }

}
