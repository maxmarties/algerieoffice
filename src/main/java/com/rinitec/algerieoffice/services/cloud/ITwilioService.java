package com.rinitec.algerieoffice.services.cloud;

public interface ITwilioService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param phone
	 * @param code
	 */
	void sendCode(String phone, String code);
	
}
