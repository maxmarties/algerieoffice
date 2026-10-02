package com.rinitec.algerieoffice.services.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.portfolio.ActualityForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IActualityService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countActuality(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	ActualityForm readActualityForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actualityForm
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws InvalidImageException
	 */
	Actuality addActuality(ActualityForm actualityForm, Long userId) throws AlreadyExistException, InvalidImageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actualityForm
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws InvalidImageException
	 */
	Actuality updateActuality(ActualityForm actualityForm, Long userId) throws AlreadyExistException, InvalidImageException;
	
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
	ElementsList findActualitiesList(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorActuality(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Actuality deleteActuality(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteActualities(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllActualities(Long companyId);
	
}
