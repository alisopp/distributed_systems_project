package com.distributed_systems.group_2.services;

import com.distributed_systems.group_2.models.request.JoinRequestEntity;
import com.distributed_systems.group_2.models.request.LeaveRequestEntity;
import com.distributed_systems.group_2.models.request.UserRequestEntity;
import com.distributed_systems.group_2.models.response.JoinResponseEntity;
import com.distributed_systems.group_2.models.response.LeaveResponseEntity;
import com.distributed_systems.group_2.models.response.UserResponseEntity;

public interface SuperServerRestService {

	/**
	 * handles a request to join the chat
	 * 
	 * @param request
	 *            data needed to join the chat
	 * @return status code
	 */
	JoinResponseEntity join(JoinRequestEntity request);

	/**
	 * handles a request to leave the chat
	 * 
	 * @param request
	 *            data needed to leave the chat
	 * @return status code
	 */
	LeaveResponseEntity leave(LeaveRequestEntity request);

	/**
	 * handles a request to get the address of a user
	 * 
	 * @param request
	 *            data needed to get the address of the user
	 * @return address of the user
	 */
	UserResponseEntity user(UserRequestEntity request);
}