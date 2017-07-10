package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	
	public Page<City> findAll(Specification<City> spec, Pageable pageable) {
		return cityRepository.findAll(spec, pageable);
	}
	
	public City findById(long id) {
		return cityRepository.findOne(id);
	}

}
