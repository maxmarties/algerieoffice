package com.rinitec.algerieoffice.services.company.team;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.team.TeamuserForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface ITeamService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countGuest(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countUserAndGuest(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	boolean hasSuperAdmin(Long id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param ids
	 * @param companyId
	 * @return
	 */
	boolean hasContentSuperAdmin(List<Long> ids, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	TeamuserForm readTeamuserForm(Long id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param teamuserForm
	 * @return
	 * @throws AlreadyExistException
	 */
	User addUser(TeamuserForm teamuserForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param teamuserForm
	 * @return
	 * @throws AlreadyExistException
	 */
	User updateUser(TeamuserForm teamuserForm) throws AlreadyExistException;
	
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
	ElementsList findUsersList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 */
	User removeUser(Long id, Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 */
	void removeUsers(List<Long> lines, Long companyId) throws NotFoundException;
	
}
