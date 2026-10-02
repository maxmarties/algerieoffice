package com.rinitec.algerieoffice.services;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.admins.CurrSocialFooter;

public interface IAttributeService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean checkCompanyPublished(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @return
	 */
	CurrentConfig attributeNotfound(Model model, String config);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @return
	 */
	CurrentUser attributeAuthentified(Model model, Authentication authentication);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @param localUser
	 * @return
	 */
	CurrentConfig attributeConfig(Model model, String config, LocalUser localUser);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	CurrentUser attributeAuthentified(Model model, Authentication authentication, String config);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	CurrentUser attributePreview(Model model, LocalUser localUser, String config);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	CurrentUser attributeExplorer(Model model, Authentication authentication, String config);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	CurrentUser attributeUser(Model model, LocalUser localUser);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @param localUser
	 * @return
	 */
	CurrentUser attributeUser(Model model, String config, LocalUser localUser);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	CurrentCompany attributeCompany(Model model, LocalUser localUser);

	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @param localUser
	 * @return
	 */
	CurrentCompany attributeCompany(Model model, String config, LocalUser localUser);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	CurrSocialFooter attributeCurrSocial(Model model, Authentication authentication, String config);
	
}
