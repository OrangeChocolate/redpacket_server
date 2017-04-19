package com.redpacket.server.security.auth.jwt.extractor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.redpacket.server.security.config.WebSecurityConfig;

/**
 * An implementation of {@link TokenExtractor} extracts token from
 * Authorization: Bearer scheme.
 * 
 * @author vladimir.stankovic
 *
 * Aug 5, 2016
 */
@Component
public class JwtHeaderOrRequestParameterTokenExtractor implements TokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
        if (StringUtils.isBlank(authorizationHeader)) {
        	String authorizationRequestToken = request.getParameter(WebSecurityConfig.JWT_TOKEN_REQUEST_PARAM);
        	if(StringUtils.isBlank(authorizationRequestToken)) {
        		throw new AuthenticationServiceException("Authorization header or request parameter cannot be blank!");
        	}
            return authorizationRequestToken;
        }

        if (authorizationHeader.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return authorizationHeader.substring(HEADER_PREFIX.length(), authorizationHeader.length());
    }
}
