package com.rinitec.algerieoffice.services.blacklist;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.tools.BlacklistForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IBlacklistCompanyService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @return
	 */
	boolean HasUserBlocked(Long userId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findBlackList(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteBlacklist(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteBlacklists(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllBlacklists(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param userId
	 * @return
	 */
	BlacklistForm readBlacklistForm(Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blacklistForm
	 * @param autorId
	 * @return
	 */
	BlacklistCompany addBlacklistCompany(BlacklistForm blacklistForm, Long autorId);
	
}
