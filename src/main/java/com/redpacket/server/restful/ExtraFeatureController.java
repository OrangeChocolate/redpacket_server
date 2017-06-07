package com.redpacket.server.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.GeneralResponse;
import com.redpacket.server.service.ExtraFeatureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin
@Api(tags = { "extra" })
@RestController
@RequestMapping("/api/extra/")
public class ExtraFeatureController {

	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	@Autowired
	private ExtraFeatureService extraFeatureService;

	@ApiOperation(value = "update user location", notes = "update user location", authorizations = {
			@Authorization(value = "token") })
	@RequestMapping(value = "location/{openId}/{latitude}/{longitude}", method = RequestMethod.PUT, produces = "application/json")
	public GeneralResponse<String> updateLocation(@PathVariable String openId, @PathVariable double latitude,
			@PathVariable double longitude) {
		boolean updateSuccess = extraFeatureService.updateLocation(openId, latitude, longitude);
		return new GeneralResponse<String>(updateSuccess ? GeneralResponse.SUCCESS : GeneralResponse.ERROR, "");

	}

}
