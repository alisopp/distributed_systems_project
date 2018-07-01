package com.distributed_systems.group_2.models.response;

import com.distributed_systems.group_2.enums.ResponseStatus;

public class UserResponseEntity {

	private ResponseStatus status;
	private String username;
	private String host;
	private int port;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}