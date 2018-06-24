package com.distributed_systems.group_2.utils;

import com.distributed_systems.group_2.constants.Constants;

public class Utils {

	public static boolean isAuthenticated(String secret) {
		return !isEmpty(secret) && secret.equals(Constants.CLIENT_AUTHENTICATION_SECRET);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}