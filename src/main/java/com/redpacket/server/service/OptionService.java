package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.City;
import com.redpacket.server.model.Option;
import com.redpacket.server.repository.CityRepository;
import com.redpacket.server.repository.OptionRepository;

@Service
public class OptionService {
	
	@Autowired
	private OptionRepository optionRepository;
	
	public List<Option> findAll() {
		return optionRepository.findAll();
	}
	
	public Option findById(long id) {
		return optionRepository.findOne(id);
	}
	
	public Option saveOrUpdate(Option option) {
		return optionRepository.save(option);
	}

}
