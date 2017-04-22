package com.redpacket.server.restful;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.common.SwaggerSecurityDefinition;
import com.redpacket.server.model.WechatUser;
import com.redpacket.server.service.WechatUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Api(tags={"wechatUser"})
@RestController
@RequestMapping("/api/wechatUser/")
public class WechatUserController implements SwaggerSecurityDefinition {
	
	public static final Logger logger = LoggerFactory.getLogger(WechatUserController.class);
	
	@Autowired
	private WechatUserService wechatUserService;
	
	@ApiOperation(value = "List all wechatUser", notes = "List all wechatUser in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<WechatUser>> get() {
		List<WechatUser> wechatUsers = wechatUserService.findAll();
		return new ResponseEntity<List<WechatUser>>(wechatUsers, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a wechatUser", notes = "Get a wechatUser by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<WechatUser> get(@PathVariable Long id) {
		WechatUser wechatUser = wechatUserService.findById(id);
		if(wechatUser == null) {
            logger.error("WechatUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("WechatUser with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<WechatUser>(wechatUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Create a wechatUser", notes = "Create a wechatUser with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<WechatUser> create(@RequestBody WechatUser wechatUser) {
		if(StringUtils.isEmpty(wechatUser.getOpenId())) {
            logger.error("WechatUser openId is empty.");
            return new ResponseEntity(new CustomErrorType("WechatUser openId is empty."), HttpStatus.BAD_REQUEST);
		}
		// delete the id safely
		wechatUser.setId(null);
		WechatUser pesistedWechatUser = wechatUserService.saveOrUpdate(wechatUser);
		return new ResponseEntity<WechatUser>(pesistedWechatUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Update a wechatUser", notes = "Update a wechatUser with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<WechatUser> update(@PathVariable("id") Long id, @RequestBody WechatUser wechatUser) {
		WechatUser pesistedWechatUser = wechatUserService.findById(id);
		if(pesistedWechatUser == null) {
            logger.error("WechatUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("WechatUser with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<WechatUser>(wechatUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete a wechatUser", notes = "Delete a wechatUser by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<WechatUser> delete(@PathVariable Long id) {
		WechatUser wechatUser = wechatUserService.findById(id);
		if(wechatUser == null) {
            logger.error("WechatUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("WechatUser with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		wechatUserService.delete(wechatUser);
		return new ResponseEntity<WechatUser>(wechatUser, HttpStatus.OK);
	}
}
