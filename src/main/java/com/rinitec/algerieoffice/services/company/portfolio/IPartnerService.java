package com.rinitec.algerieoffice.services.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.portfolio.PartneruserForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IPartnerService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countGuestPartner(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countPartnerAndGuest(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	PartneruserForm readPartneruserForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param partneruserForm
	 * @param userId
	 * @return
	 */
	Partner addPartner(PartneruserForm partneruserForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param partneruserForm
	 * @param userId
	 * @return
	 */
	Partner updatePartner(PartneruserForm partneruserForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findPartnersList(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorPartner(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Partner deletePartner(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deletePartners(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllPartners(Long companyId);
	
}
