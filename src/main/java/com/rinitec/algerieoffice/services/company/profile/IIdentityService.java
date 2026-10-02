package com.rinitec.algerieoffice.services.company.profile;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyIdentity;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.admins.datas.IdentityResponseForm;
import com.rinitec.algerieoffice.web.form.company.profile.IdentityForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.data.IdentityDetail;
import com.rinitec.algerieoffice.web.modal.company.profile.IdentityState;

public interface IIdentityService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean existsByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	boolean existsByURL(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	IdentityState readIdentityState(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	IdentityForm readIdentityForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identityForm
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws NotFoundException
	 * @throws UrlUnavailableException
	 */
	CompanyIdentity updateCompanyIdentity(IdentityForm identityForm, Long userId) throws AlreadyExistException, NotFoundException, UrlUnavailableException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findIdentitiesList(String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	IdentityDetail readIdentityDetail(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param adminId
	 * @param identityResponseForm
	 * @return
	 */
	Object[] validateIdentity(Long adminId, IdentityResponseForm identityResponseForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 */
	String deleteIdentity(Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	void deleteIdentities(List<Long> lines);
	
}
