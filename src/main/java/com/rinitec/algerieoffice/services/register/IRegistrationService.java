package com.rinitec.algerieoffice.services.register;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.CompanymailExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.oauth2.OAuth2AuthenticationProcessingException;
import com.rinitec.algerieoffice.web.form.register.CompanyForm;
import com.rinitec.algerieoffice.web.form.register.UserForm;
import com.rinitec.algerieoffice.web.form.user.AddcompanyForm;

public interface IRegistrationService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userForm
	 * @return
	 * @throws AlreadyExistException
	 */
	User registerNewUser(UserForm userForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws NotFoundException
	 * @throws PhoneExistException
	 * @throws CompanymailExistException
	 */
	User registerNewCompany(CompanyForm companyForm) throws AlreadyExistException, NotFoundException, PhoneExistException, CompanymailExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param addcompanyForm
	 * @return
	 * @throws NotFoundException
	 * @throws PhoneExistException
	 * @throws CompanymailExistException
	 */
	User addNewCompany(User user, AddcompanyForm addcompanyForm) throws NotFoundException, PhoneExistException, CompanymailExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param provider
	 * @param attributes
	 * @param idToken
	 * @param userInfo
	 * @return
	 * @throws OAuth2AuthenticationProcessingException
	 */
	LocalUser registerOrLogin(String provider, Map<String, Object> attributes, 
			OidcIdToken idToken, OidcUserInfo userInfo) throws OAuth2AuthenticationProcessingException;
	
}
