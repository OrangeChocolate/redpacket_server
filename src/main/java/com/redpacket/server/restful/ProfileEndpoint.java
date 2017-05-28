package com.redpacket.server.restful;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.security.auth.JwtAuthenticationToken;
import com.redpacket.server.security.model.UserContext;

/**
 * End-point for retrieving logged-in user details.
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
@CrossOrigin
@RestController
public class ProfileEndpoint {
    @RequestMapping(value="/api/me", method=RequestMethod.GET)
    public @ResponseBody UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }
}
