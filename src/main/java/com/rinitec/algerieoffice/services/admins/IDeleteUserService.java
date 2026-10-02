package com.rinitec.algerieoffice.services.admins;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;

public interface IDeleteUserService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @throws AccessAuthorityException
	 */
	void deleteUser(User user) throws AccessAuthorityException;
	
}
