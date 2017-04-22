package com.redpacket.server.security.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.model.AdminUser;
import com.redpacket.server.security.auth.jwt.extractor.TokenExtractor;
import com.redpacket.server.security.auth.jwt.verifier.TokenVerifier;
import com.redpacket.server.security.config.JwtSettings;
import com.redpacket.server.security.config.WebSecurityConfig;
import com.redpacket.server.security.exceptions.InvalidJwtToken;
import com.redpacket.server.security.model.UserContext;
import com.redpacket.server.security.model.token.JwtToken;
import com.redpacket.server.security.model.token.JwtTokenFactory;
import com.redpacket.server.security.model.token.RawAccessJwtToken;
import com.redpacket.server.security.model.token.RefreshToken;
import com.redpacket.server.service.AdminUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@CrossOrigin
@Api(tags={"auth"})
@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private AdminUserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired private TokenExtractor tokenExtractor;
    
    @ApiOperation(value = "Refresh Token", notes = "Refresh Token")
    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request);
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        AdminUser user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
