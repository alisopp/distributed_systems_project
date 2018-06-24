package com.distributed_systems.group_2.services;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.distributed_systems.group_2.enums.ResponseStatus;
import com.distributed_systems.group_2.models.request.JoinRequestEntity;
import com.distributed_systems.group_2.models.request.LeaveRequestEntity;
import com.distributed_systems.group_2.models.request.UserRequestEntity;
import com.distributed_systems.group_2.models.response.JoinResponseEntity;
import com.distributed_systems.group_2.models.response.LeaveResponseEntity;
import com.distributed_systems.group_2.models.response.UserResponseEntity;
import com.distributed_systems.group_2.threads.JoinRequestThread;
import com.distributed_systems.group_2.threads.LeaveRequestThread;
import com.distributed_systems.group_2.threads.UserRequestThread;

@Path("/")
public class SuperServerRestServiceImpl implements SuperServerRestService {

	public static Map<String, InetSocketAddress> users = new HashMap<String, InetSocketAddress>();

	@POST
	@Path("/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JoinResponseEntity join(JoinRequestEntity request) {
		JoinResponseEntity response = null;

		JoinRequestThread joinRequestThread = new JoinRequestThread(request);
		try {
			joinRequestThread.join();
			response = joinRequestThread.getResponse();
		} catch (InterruptedException e) {
			response = joinRequestThread.getResponse();
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
		}

		return response;
	}

	@POST
	@Path("/leave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LeaveResponseEntity leave(LeaveRequestEntity request) {
		LeaveResponseEntity response = null;

		LeaveRequestThread joinRequestThread = new LeaveRequestThread(request);
		try {
			joinRequestThread.join();
			response = joinRequestThread.getResponse();
		} catch (InterruptedException e) {
			response = joinRequestThread.getResponse();
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
		}

		return response;
	}

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponseEntity user(UserRequestEntity request) {
		UserResponseEntity response = null;

		UserRequestThread joinRequestThread = new UserRequestThread(request);
		try {
			joinRequestThread.join();
			response = joinRequestThread.getResponse();
		} catch (InterruptedException e) {
			response = joinRequestThread.getResponse();
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
		}

		return response;
	}
}