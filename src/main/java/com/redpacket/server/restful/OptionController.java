package com.redpacket.server.restful;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.model.Option;
import com.redpacket.server.service.OptionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin
@Api(tags={"option"})
@RestController
@RequestMapping("/api/option/")
public class OptionController {

	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private OptionService optionService;
	
	@ApiOperation(value = "List all options", notes = "List all options in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Option>> get() {
		List<Option> options = optionService.findAll();
		return new ResponseEntity<List<Option>>(options, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a option", notes = "Get a option in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Option> get(@PathVariable Long id) {
		Option option = optionService.findById(id);
		return new ResponseEntity<Option>(option, HttpStatus.OK);
	}

	@ApiOperation(value = "Update a product", notes = "Update a product with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Option> update(@PathVariable("id") Long id, @RequestBody Option option) {
		Option pesistedOption = optionService.saveOrUpdate(option);
		return new ResponseEntity<Option>(pesistedOption, HttpStatus.OK);
	}
}
