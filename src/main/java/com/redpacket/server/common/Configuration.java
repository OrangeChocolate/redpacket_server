package com.redpacket.server.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redpacket.server.model.Option;

public class Configuration {
	
	
	public static String force_city_check_key = "force_city_check";
	public static String max_redpackets_user_total_key = "max_redpackets_user_total";
	public static String max_redpackets_user_daily_key = "max_redpackets_user_daily";
	public static String wechat_wishing_key = "wechat_wishing";
	public static String wechat_act_name_key = "wechat_act_name";
	public static String wechat_remark_key = "wechat_remark";
	public static String wechat_send_name_key = "wechat_send_name";
	
	public static Map<String, Option> optionMap = new HashMap<String, Option>();
	
	public static String secret = "!@#$%";
	
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
