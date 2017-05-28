package com.redpacket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.redpacket.server.common.Configuration;
import com.redpacket.server.service.OptionService;

@Component
public class ServerInitializer implements ApplicationRunner {

	public static final Logger logger = LoggerFactory.getLogger(ServerInitializer.class);
	
	@Autowired
	OptionService optionService;
	
	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		// 初始化配置参数
		Configuration.loadOption(optionService.findAll());
	}


}