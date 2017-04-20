package com.redpacket.server.restful;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiKeyAuthDefinition.ApiKeyLocation;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;


@SwaggerDefinition(securityDefinition = @SecurityDefinition(
		apiKeyAuthDefintions = {
				@ApiKeyAuthDefinition(key = "token", name = "token", in = ApiKeyLocation.QUERY) 
		}
	)
)
public interface SwaggerSecurityDefinition {

}