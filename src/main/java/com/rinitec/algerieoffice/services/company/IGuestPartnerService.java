package com.rinitec.algerieoffice.services.company;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.portfolio.PartnerguestForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IGuestPartnerService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param partnerguestForm
	 * @param companyId
	 * @param autorId
	 * @return
	 * @throws NotFoundException
	 * @throws AccessLeaderException
	 * @throws AlreadyExistException
	 */
	GuestPartner addGuestPartner(PartnerguestForm partnerguestForm, Long companyId, Long autorId) throws NotFoundException, 
		AccessLeaderException, AlreadyExistException;
	
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
	ElementsList findPartnerGuestList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws AccessLeaderException
	 * @throws InvalidResourceException
	 */
	GuestPartner validateGuestPartner(String id, Long companyId, Long userId) throws NotFoundException, AccessLeaderException, 
		InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Long deleteGuestPartner(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	List<Long> deleteGuestPartners(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<Long> deleteAllGuestPartners(Long companyId);
	
}
