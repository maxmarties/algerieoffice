package com.rinitec.algerieoffice.services.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.portfolio.WorkForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IWorkService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countWork(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllChosePartner(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	WorkForm readWorkForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param workForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 * @throws MaxKeyswordException
	 * @throws InvalidImageException
	 */
	Work addWork(WorkForm workForm, int maxKeysword, Long userId) throws NotFoundException, UrlUnavailableException, AlreadyExistException, 
		MaxKeyswordException, InvalidImageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param workForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 * @throws MaxKeyswordException
	 * @throws InvalidImageException
	 */
	Work updateWork(WorkForm workForm, int maxKeysword, Long userId) throws NotFoundException, UrlUnavailableException, AlreadyExistException, 
		MaxKeyswordException, InvalidImageException;
	
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
	ElementsList findWorksList(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllFilterPartner(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Work deleteWork(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteWorks(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllWorks(Long companyId);
	
}
