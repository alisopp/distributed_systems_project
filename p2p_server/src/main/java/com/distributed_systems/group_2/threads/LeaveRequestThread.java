package com.distributed_systems.group_2.threads;

import com.distributed_systems.group_2.enums.ResponseStatus;
import com.distributed_systems.group_2.models.request.LeaveRequestEntity;
import com.distributed_systems.group_2.models.response.LeaveResponseEntity;
import com.distributed_systems.group_2.services.SuperServerRestServiceImpl;
import com.distributed_systems.group_2.utils.Utils;

public class LeaveRequestThread extends Thread {

	private LeaveRequestEntity request;
	private LeaveResponseEntity response;

	public LeaveRequestThread(LeaveRequestEntity request) {
		super();

		this.request = request;
		this.response = new LeaveResponseEntity();

		this.start();
	}

	@Override
	public void run() {
		super.run();

		LeaveResponseEntity response = new LeaveResponseEntity();

		if (request != null) {

			if (Utils.isAuthenticated(request.getSecret())) {

				if (SuperServerRestServiceImpl.users.containsKey(request.getUsername())) {
					SuperServerRestServiceImpl.users.remove(request.getUsername());

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

	public LeaveResponseEntity getResponse() {
		return this.response;
	}
}