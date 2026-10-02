package com.rinitec.algerieoffice.persistence.dao.users.profiles;

import org.joda.time.DateTime;

public interface AccountRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param pro
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countAllLogin(boolean pro, DateTime begin, DateTime end);
	
}
