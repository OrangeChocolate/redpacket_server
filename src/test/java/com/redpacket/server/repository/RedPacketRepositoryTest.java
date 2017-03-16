package com.redpacket.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.redpacket.server.model.RedPacket;
import com.redpacket.server.model.WechatUser;

// see https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-data-jpa/src/test/java/sample/data/jpa/service/CityRepositoryIntegrationTests.java
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedPacketRepositoryTest {
	
	@Autowired
	RedPacketRepository redPacketRepository;
	@Autowired
	WechatUserRepository wechatUserRepository;

	@Test
	public void test_a_insertWechatUser() {
		WechatUser wechatUser = new WechatUser("liudonghua", "iman", "male", "", "", "", "", "");
		wechatUserRepository.save(wechatUser);
		assertThat(wechatUserRepository.findAll().size()).isEqualTo(1);
	}
	
	@Test
	public void test_b_insertRedPacket() {
		WechatUser wechatUser = wechatUserRepository.findByNickname("iman");
		RedPacket redPacket1 = new RedPacket(wechatUser, 10, new Date());
		RedPacket redPacket2 = new RedPacket(wechatUser, 100, new Date());
		redPacketRepository.save(redPacket1);
		redPacketRepository.save(redPacket2);
		assertThat(redPacketRepository.findAll().size()).isEqualTo(2);
	}
	
	@Test
	public void test_c_findRelatedRedPacket() {
		WechatUser wechatUser = wechatUserRepository.findByNickname("iman");
		assertThat(wechatUser.getRedPackets().size()).isEqualTo(2);
	}
}
