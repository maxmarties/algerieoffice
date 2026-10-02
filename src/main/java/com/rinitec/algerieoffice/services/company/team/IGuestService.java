package com.rinitec.algerieoffice.services.company.team;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.team.TeamguestForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IGuestService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param teamguestForm
	 * @param companyId
	 * @param autorId
	 * @return
	 * @throws NotFoundException
	 * @throws AccessLeaderException
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 */
	Guest addGuest(TeamguestForm teamguestForm, Long companyId, Long autorId) throws NotFoundException, AccessLeaderException, 
		UrlUnavailableException, AlreadyExistException;

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findUserContributorList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param user
	 * @return
	 * @throws NotFoundException
	 */
	Guest validateUserGuest(Long id, User user) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 */
	void deleteUserGuest(Long id, Long userId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 */
	void deleteUserGuests(List<Long> lines, Long userId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllUserGuests(Long userId);
	
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
	ElementsList findGuestList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 */
	void deleteCompanyGuest(Long id, Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 */
	void deleteCompanyGuests(List<Long> lines, Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllCompanyGuests(Long companyId);
	
}
