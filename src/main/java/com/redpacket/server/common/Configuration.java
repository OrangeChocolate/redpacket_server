package com.redpacket.server.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redpacket.server.model.Option;

public class Configuration {
	
	public static String force_city_check_key = "force_city_check";
	public static String max_redpackets_user_total_key = "max_redpackets_user_total";
	public static String max_redpackets_user_daily_key = "max_redpackets_user_daily";
	public static String redpacket_wishing_key = "redpacket_wishing";
	public static String redpacket_act_name_key = "redpacket_act_name";
	public static String redpacket_remark_key = "redpacket_remark";
	public static String wechat_send_name_key = "wechat_send_name";
	// 微信认证成功页面显示的标题和内容
	public static String wechat_user_title_key = "wechat_user_title";
	public static String wechat_user_text_key = "wechat_user_text";
	// 微信分享弹窗的显示的标题、链接、缩略图链接
	public static String wechat_share_title_key = "wechat_share_title";
	public static String wechat_share_link_key = "wechat_share_link";
	public static String wechat_share_imgUrl_key = "wechat_share_imgUrl";
	
	public static Map<String, Option> optionMap = new HashMap<String, Option>();
	
//	public static String secret = "!@#$%";
	
	public static void loadOption(List<Option> options) {
		options.stream().forEach(option -> {
			optionMap.put(option.getName(), option);
		});
	}
	
	public static Option updateOption(Option option) {
		optionMap.put(option.getName(), option);
		return option;
	}
	
	public static Option getOption(String optionKey) {
		Option option = optionMap.get(optionKey);
		return option;
	}
}
