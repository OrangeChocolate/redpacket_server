package com.redpacket.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.redpacket.server.common.CustomAdminUserRoleDeserializer;


/**
 * UserRole
 */
@Entity
@JsonDeserialize(using = CustomAdminUserRoleDeserializer.class)
public class AdminUserRole implements Serializable {

	private static final long serialVersionUID = -2837556926525951232L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.WRITE_ONLY)
	protected Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	protected Role role;

	@ManyToMany(mappedBy = "roles")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<AdminUser> adminUsers;

	public AdminUserRole() {
	}

	public AdminUserRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<AdminUser> getAdminUsers() {
		return adminUsers;
	}

	public void setAdminUsers(List<AdminUser> adminUsers) {
		this.adminUsers = adminUsers;
	}
}
