package com.redpacket.server.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.redpacket.server.model.City;

import static org.assertj.core.api.Assertions.assertThat;

// see https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-data-jpa/src/test/java/sample/data/jpa/service/CityRepositoryIntegrationTests.java
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatUserRepositoryTest {

	@Autowired
	RedPacketRepository redPacketRepository;
	@Autowired
	WechatUserRepository wechatUserRepository;

}
