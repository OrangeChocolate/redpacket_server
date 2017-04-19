package com.redpacket.server.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.redpacket.server.model.AdminUserRole;
import com.redpacket.server.model.Role;

public class CustomAdminUserRoleDeserializer extends JsonDeserializer<AdminUserRole> {

	@Override
	public AdminUserRole deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
		return new AdminUserRole(Role.fromString(node.get("role").asText()));
	}

}
