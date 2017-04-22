package com.redpacket.server.restful;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.security.auth.ajax.LoginRequest;
import com.redpacket.server.security.model.token.AccessJwtToken;
import com.redpacket.server.security.model.token.JwtToken;
import com.redpacket.server.security.model.token.RefreshToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * 
 * @author Liu.D.H
 * 
 * This is just a fake control for springfox-swagger2 to generate api-docs
 *
 */
@Api(tags={"auth"})
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	
//	@ApiOperation(value = "Refresh Token", notes = "Refresh Token")
//	@RequestMapping(value = "/token", method = RequestMethod.GET, produces = "application/json")
//	public JwtToken refreshToken(@RequestBody JwtToken token) {
//		Jws<Claims> jws = null;
//		return new RefreshToken(jws);
//	}

	@ApiOperation(value = "Get Token", notes = "Get Token")
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public JwtToken refreshToken(@RequestBody LoginRequest loginRequest) {
		Claims claims = null;
		return new AccessJwtToken("token", claims);
	}
	
}
