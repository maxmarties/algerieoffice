package com.rinitec.algerieoffice.services.admins.team;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.team.AdmManagerForm;
import com.rinitec.algerieoffice.web.form.admins.team.AdmUserForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAdmUserService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmUsersList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admUserForm
	 * @return
	 * @throws AlreadyExistException
	 */
	User addUser(AdmUserForm admUserForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 */
	User lockUser(Long userId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 */
	User deleteUser(Long userId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	void deleteUsers(List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmModeratorsList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmManagersList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmManagerForm readAdmManagerForm(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admManagerForm
	 * @return
	 * @throws AlreadyExistException
	 */
	User addManager(AdmManagerForm admManagerForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admManagerForm
	 * @return
	 * @throws AlreadyExistException
	 */
	User updateManager(AdmManagerForm admManagerForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 */
	User lockManager(Long userId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	User removeManager(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	void removeManagers(List<Long> lines);
	
}
