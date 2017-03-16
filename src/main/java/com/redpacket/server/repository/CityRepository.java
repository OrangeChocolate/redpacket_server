package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
