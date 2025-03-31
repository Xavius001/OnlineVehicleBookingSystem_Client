package com.techwave.client.model;

import javax.validation.constraints.NotBlank;

public class Logindb {
	@NotBlank(message="required")
	private String userId;
	@NotBlank(message="required")
	private String password;
	private String role;
	private String status;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Logindb(String userId, String password, String role, String status) {
		super();
		this.userId = userId;
		this.password = password;
		this.role = role;
		this.status = status;
	}

	public Logindb() {
		super();
		this.userId = "";
		this.password = "";
		this.role = "";
		this.status = "";
	}
	
}
