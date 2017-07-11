package com.redpacket.server.restful;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.Utils;
import com.redpacket.server.model.City;
import com.redpacket.server.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@CrossOrigin
@Api(tags={"city"})
@RestController
@RequestMapping("/api/city/")
public class CityController {

	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private CityService cityService;
	
	@ApiOperation(value = "List all cities", notes = "List all cities in json response", authorizations={@Authorization(value = "token")})
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", paramType = "query"),
		@ApiImplicitParam(name = "page", defaultValue="0", paramType = "query"),
		@ApiImplicitParam(name = "size", defaultValue = "10", paramType = "query"),
		@ApiImplicitParam(name = "sort", defaultValue = "updateDate,desc", paramType = "query") })
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<City>> get(HttpServletRequest request, HttpServletResponse response,
			@Spec(path = "name", spec = Like.class) Specification<City> spec,
	        @PageableDefault(size = 1000, sort = "id") Pageable pageable) {
		Page<City> page = cityService.findAll(spec, pageable);
		List<City> cities = page.getContent();
		Utils.setExtraHeader(response, page);
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a city", notes = "Get a city in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<City> get(@PathVariable Long id) {
		City city = cityService.findById(id);
		return new ResponseEntity<City>(city, HttpStatus.OK);
	}
}
