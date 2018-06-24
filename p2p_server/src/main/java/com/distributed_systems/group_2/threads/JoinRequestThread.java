package com.distributed_systems.group_2.threads;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import com.distributed_systems.group_2.enums.ResponseStatus;
import com.distributed_systems.group_2.models.request.JoinRequestEntity;
import com.distributed_systems.group_2.models.response.JoinResponseEntity;
import com.distributed_systems.group_2.services.SuperServerRestServiceImpl;
import com.distributed_systems.group_2.utils.Utils;

public class JoinRequestThread extends Thread {

	private JoinRequestEntity request;
	private JoinResponseEntity response;

	public JoinRequestThread(JoinRequestEntity request) {
		super();

		this.request = request;
		this.response = new JoinResponseEntity();

		this.start();
	}

	@Override
	public void run() {
		super.run();

		JoinResponseEntity response = new JoinResponseEntity();

		if (request != null) {

			if (Utils.isAuthenticated(request.getSecret())) {

				if (!SuperServerRestServiceImpl.users.containsKey(request.getUsername())) {
					InetAddress inetAddress;
					try {
						inetAddress = InetAddress.getByName(request.getHost());
						InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, request.getPort());
						SuperServerRestServiceImpl.users.put(request.getUsername(), inetSocketAddress);

						response.setStatus(ResponseStatus.SUCCESS);
					} catch (UnknownHostException e) {
						response.setStatus(ResponseStatus.UNKNOWN_ERROR);
					}
				} else {
					response.setStatus(ResponseStatus.USERNAME_ALREADY_EXISTS);
				}
			} else {
				response.setStatus(ResponseStatus.AUTHENTICATION_ERROR);
			}
		} else {
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
		}

		this.response = response;
	}

	public JoinResponseEntity getResponse() {
		return this.response;
	}
}