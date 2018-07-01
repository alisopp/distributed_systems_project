package com.distributed_systems.group_2.models.response;

import com.distributed_systems.group_2.enums.ResponseStatus;

public class LeaveResponseEntity {

	private ResponseStatus status;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
}