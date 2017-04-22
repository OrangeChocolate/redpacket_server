package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.City;
import com.redpacket.server.repository.CityRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	public List<City> findAll() {
		return cityRepository.findAll();
	}
	
	public City findById(long id) {
		return cityRepository.findOne(id);
	}

}
