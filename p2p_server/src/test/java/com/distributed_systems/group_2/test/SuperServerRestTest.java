package com.distributed_systems.group_2.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.distributed_systems.group_2.constants.Constants;
import com.distributed_systems.group_2.enums.ResponseStatus;
import com.distributed_systems.group_2.models.request.JoinRequestEntity;
import com.distributed_systems.group_2.models.request.LeaveRequestEntity;
import com.distributed_systems.group_2.models.request.UserRequestEntity;
import com.distributed_systems.group_2.models.response.JoinResponseEntity;
import com.distributed_systems.group_2.models.response.UserResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SuperServerRestTest {

	private static final String HOST_NAME = "http://localhost:8080";
	private static final String BASIC_URL = HOST_NAME + "/SUPERSERVER-JAXRS-JSON";
	private static final String REST_PREFIX = "/rest";
	private static final String REST_BASIC_URL = BASIC_URL + REST_PREFIX;

	private static final String JOIN_REST_URL = REST_BASIC_URL + "/join";
	private static final String LEAVE_REST_URL = REST_BASIC_URL + "/leave";
	private static final String USER_REST_URL = REST_BASIC_URL + "/user";

	private static final String HOST = "localhost";
	private static final int PORT = 49152;
	private static final String USERNAME = "fabian";

	private static final int HTTP_OK_STATUS = 200;

	private Client client = Client.create();

	public SuperServerRestTest() {
		super();
	}

	@Test
	public void test1Join() throws Exception {
		WebResource webResource = client.resource(JOIN_REST_URL);
		ObjectMapper objectMapper = new ObjectMapper();

		JoinRequestEntity request = new JoinRequestEntity();
		request.setUsername(USERNAME);
		request.setHost(HOST);
		request.setPort(PORT);
		request.setSecret("wrong_secret");

		for (int i = 0; i < 3; i++) {
			String jsonRequest = objectMapper.writeValueAsString(request);
			ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, jsonRequest);

			assertTrue(clientResponse.getStatus() == HTTP_OK_STATUS);

			String jsonResponse = clientResponse.getEntity(String.class);
			JoinResponseEntity response = objectMapper.readValue(jsonResponse, JoinResponseEntity.class);

			switch (i) {
			case 0: {
				assertEquals(ResponseStatus.AUTHENTICATION_ERROR, response.getStatus());
				request.setSecret(Constants.CLIENT_AUTHENTICATION_SECRET);

				break;
			}

			case 1: {
				assertEquals(ResponseStatus.SUCCESS, response.getStatus());

				break;
			}

			case 2: {
				assertEquals(ResponseStatus.USERNAME_ALREADY_EXISTS, response.getStatus());
				break;
			}

			default: {
				break;
			}
			}
		}
	}

	@Test
	public void test2User() throws Exception {
		WebResource webResource = client.resource(USER_REST_URL);
		ObjectMapper objectMapper = new ObjectMapper();

		UserRequestEntity request = new UserRequestEntity();
		request.setUsername("not_existing_user");
		request.setSecret("wrong_secret");

		for (int i = 0; i < 3; i++) {
			String jsonRequest = objectMapper.writeValueAsString(request);
			ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, jsonRequest);

			assertTrue(clientResponse.getStatus() == HTTP_OK_STATUS);

			String jsonResponse = clientResponse.getEntity(String.class);
			UserResponseEntity response = objectMapper.readValue(jsonResponse, UserResponseEntity.class);

			switch (i) {
			case 0: {
				assertEquals(ResponseStatus.AUTHENTICATION_ERROR, response.getStatus());
				request.setSecret(Constants.CLIENT_AUTHENTICATION_SECRET);

				break;
			}

			case 1: {
				assertEquals(ResponseStatus.USERNAME_DOES_NOT_EXISTS, response.getStatus());
				request.setUsername(USERNAME);

				break;
			}

			case 2: {
				assertEquals(ResponseStatus.SUCCESS, response.getStatus());
				assertEquals(response.getUsername(), USERNAME);
				assertEquals(response.getHost(), HOST);
				assertEquals(response.getPort(), PORT);

				break;
			}

			default: {
				break;
			}
			}
		}
	}

	@Test
	public void test3Leave() throws Exception {
		WebResource webResource = client.resource(LEAVE_REST_URL);
		ObjectMapper objectMapper = new ObjectMapper();

		LeaveRequestEntity request = new LeaveRequestEntity();
		request.setUsername("not_existing_user");
		request.setSecret("wrong_secret");

		for (int i = 0; i < 3; i++) {
			String jsonRequest = objectMapper.writeValueAsString(request);
			ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, jsonRequest);

			assertTrue(clientResponse.getStatus() == HTTP_OK_STATUS);

			String jsonResponse = clientResponse.getEntity(String.class);
			JoinResponseEntity response = objectMapper.readValue(jsonResponse, JoinResponseEntity.class);

			switch (i) {
			case 0: {
				assertEquals(ResponseStatus.AUTHENTICATION_ERROR, response.getStatus());
				request.setSecret(Constants.CLIENT_AUTHENTICATION_SECRET);

				break;
			}

			case 1: {
				assertEquals(ResponseStatus.USERNAME_DOES_NOT_EXISTS, response.getStatus());
				request.setUsername(USERNAME);

				break;
			}

			case 2: {
				assertEquals(ResponseStatus.SUCCESS, response.getStatus());

				break;
			}

			default: {
				break;
			}
			}
		}
	}
}