package com.redpacket.server.model;

/**
 * Enumerated {@link AdminUser} roles.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 16, 2016
 */
public enum Role {

	ADMIN("ADMIN"), USER("USER");

	private String roleName;

	Role(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public static Role fromString(String roleName) {
		for (Role role : Role.values()) {
			if (role.roleName.equalsIgnoreCase(roleName)) {
				return role;
			}
		}
		throw new IllegalArgumentException("No role " + roleName + " exist");
	}

	public String authority() {
		return "ROLE_" + this.name();
	}
}
