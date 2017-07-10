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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.Configuration;
import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.common.Utils;
import com.redpacket.server.model.Option;
import com.redpacket.server.service.OptionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@CrossOrigin
@Api(tags={"option"})
@RestController
@RequestMapping("/api/option/")
public class OptionController {

	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private OptionService optionService;
	
	@ApiOperation(value = "List all options", notes = "List all options in json response", authorizations={@Authorization(value = "token")})
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", paramType = "query"),
		@ApiImplicitParam(name = "page", defaultValue="0", paramType = "query"),
		@ApiImplicitParam(name = "size", defaultValue = "10", paramType = "query"),
		@ApiImplicitParam(name = "sort", defaultValue = "updateDate,desc", paramType = "query") })
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Option>> get(HttpServletRequest request, HttpServletResponse response, 
			@Spec(path = "name", spec = Like.class) Specification<Option> spec,
	        @PageableDefault(size = 1000, sort = "id") Pageable pageable) {
		Page<Option> page = optionService.findAll(spec, pageable);
		List<Option> options = page.getContent();
		response.setHeader("X-Total-Count", String.valueOf(page.getTotalElements()));
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
		Option existOption = optionService.findById(id);
		if(existOption == null) {
            logger.error("Option with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Option with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		Option mergedOption = Utils.mergeObjects(existOption, option);
		Option pesistedOption = optionService.saveOrUpdate(mergedOption);
		// 重新加载option
		Configuration.loadOption(optionService.findAll());
		return new ResponseEntity<Option>(pesistedOption, HttpStatus.OK);
	}
}
