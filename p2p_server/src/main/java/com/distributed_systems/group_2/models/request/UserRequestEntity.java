package com.distributed_systems.group_2.models.request;

public class UserRequestEntity {

	private String username;
	private String secret;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}