package com.rinitec.algerieoffice.services.user.account;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;

public interface IAccountService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	Account activateAccount(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param userAgent
	 * @return
	 */
	Account registerVisit(User user, String userAgent);
	
}
