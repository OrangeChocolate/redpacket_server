package com.redpacket.server.restful;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.model.City;
import com.redpacket.server.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Api(tags={"city"})
@RestController
@RequestMapping("/api/city/")
public class CityController {

	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private CityService cityService;
	
	@ApiOperation(value = "List all cities", notes = "List all cities in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<City>> get() {
		List<City> cities = cityService.findAll();
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a city", notes = "Get a city in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<City> get(@PathVariable Long id) {
		City city = cityService.findById(id);
		return new ResponseEntity<City>(city, HttpStatus.OK);
	}
}
