package com.redpacket.server.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.common.GeocoderResult;
import com.redpacket.server.model.City;
import com.redpacket.server.model.WechatUser;
import com.redpacket.server.repository.CityRepository;

@Service
public class ExtraFeatureService {
	@Autowired
	private WechatUserService wechatUserService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ApplicationProperties applicationProperties;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	String urlTemplateString = "http://apis.map.qq.com/ws/geocoder/v1/?location=%f,%f&key=%s";
	
	public boolean updateLocation(String openId, double latitude, double longitude) {
		logger.info("收到来自{}的更新地理位置信息", openId);
        WechatUser wechatUser = wechatUserService.findByOpenId(openId);
        if(wechatUser != null) {
            logger.info("更新{}数据库中的地理位置信息", openId);
        	wechatUser.setLatitude(latitude);
        	wechatUser.setLongitude(longitude);
        	// 可以根据经纬度获取用户的真实位置信息
    		String url = String.format(urlTemplateString, latitude, longitude, applicationProperties.getMap_key());
        	GeocoderResult geocoderResult = restTemplate.getForObject(url, GeocoderResult.class);
        	String cityString = geocoderResult.getResult().getAddressComponent().getCity();
            logger.info("更新{}数据库中的地理位置城市信息{}", wechatUser.getNickname(), cityString);
        	wechatUser.setActualCity(cityString);
        	wechatUser.setUpdateDate(new Date());
        	wechatUserService.saveOrUpdate(wechatUser);
        	return true;
        }
        return false;
	}

}
