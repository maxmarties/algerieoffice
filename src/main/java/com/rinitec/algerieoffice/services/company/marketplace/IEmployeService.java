package com.rinitec.algerieoffice.services.company.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.marketplace.EmployeForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IEmployeService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasMaxEmploye(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countTrashedEmploye(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	EmployeForm readEmployeForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws MaxKeyswordException
	 */
	Employe addEmploye(EmployeForm employeForm, int maxKeysword, Long userId) throws AlreadyExistException, UrlUnavailableException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws MaxKeyswordException
	 */
	Employe updateEmploye(EmployeForm employeForm, int maxKeysword, Long userId) throws AlreadyExistException, UrlUnavailableException, MaxKeyswordException;
	
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
	ElementsList findEmployesList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Employe publishEmploye(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Employe trashEmploye(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void trashEmployes(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void trashAllEmployes(Long companyId);
	
}
