package com.redpacket.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class AdminUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String username;

	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "admin_user_role_join", joinColumns = @JoinColumn(name = "admin_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_ADMIN_USER_ID")), inverseJoinColumns = @JoinColumn(name = "admin_user_role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="FK_ADMIN_USER_ROLE_ID")))
	private List<AdminUserRole> roles;

	public AdminUser() {
	}

	public AdminUser(Long id, String username, String password, List<AdminUserRole> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AdminUserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<AdminUserRole> roles) {
		this.roles = roles;
	}
}
