package com.rinitec.algerieoffice.services.user.account;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.user.account.CoordinatesForm;
import com.rinitec.algerieoffice.web.form.user.account.IdentitiesForm;
import com.rinitec.algerieoffice.web.form.user.account.LoginForm;

public interface IProfileService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	CoordinatesForm readCoordinatesForm(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param coordinatesForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws PhoneExistException
	 */
	Profile updateProfile(CoordinatesForm coordinatesForm) throws AlreadyExistException, PhoneExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
	IdentitiesForm readIdentitiesForm(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param provider
	 */
	void deleteIdentity(Long userId, String provider);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	String getPhoneProfile(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pseudo
	 * @return
	 */
	boolean existsByPseudo(String pseudo);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
	LoginForm readLoginForm(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param loginForm
	 * @param user
	 * @return
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 */
	User updateLogin(LoginForm loginForm, User user) throws UrlUnavailableException, AlreadyExistException;
	
}
