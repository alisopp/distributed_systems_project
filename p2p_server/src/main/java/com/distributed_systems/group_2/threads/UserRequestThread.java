package com.distributed_systems.group_2.threads;

import java.net.InetSocketAddress;

import com.distributed_systems.group_2.enums.ResponseStatus;
import com.distributed_systems.group_2.models.request.UserRequestEntity;
import com.distributed_systems.group_2.models.response.UserResponseEntity;
import com.distributed_systems.group_2.services.SuperServerRestServiceImpl;
import com.distributed_systems.group_2.utils.Utils;

public class UserRequestThread extends Thread {

	private UserRequestEntity request;
	private UserResponseEntity response;

	public UserRequestThread(UserRequestEntity request) {
		super();

		this.request = request;
		this.response = new UserResponseEntity();

		this.start();
	}

	@Override
	public void run() {
		super.run();

		UserResponseEntity response = new UserResponseEntity();

		if (request != null) {

			if (Utils.isAuthenticated(request.getSecret())) {

				if (SuperServerRestServiceImpl.users.containsKey(request.getUsername())) {
					InetSocketAddress inetSocketAddress = SuperServerRestServiceImpl.users.get(request.getUsername());

					response.setUsername(request.getUsername());
					response.setHost(inetSocketAddress.getHostName());
					response.setPort(inetSocketAddress.getPort());
					response.setStatus(ResponseStatus.SUCCESS);
				} else {
					response.setStatus(ResponseStatus.USERNAME_DOES_NOT_EXISTS);
				}

			} else {
				response.setStatus(ResponseStatus.AUTHENTICATION_ERROR);
			}
		} else {
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
		}

		this.response = response;
	}

	public UserResponseEntity getResponse() {
		return this.response;
	}
}