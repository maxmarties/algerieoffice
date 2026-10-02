package com.rinitec.algerieoffice.services.admins.feedback;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAdmSupportService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param recevied
	 * @return
	 */
	List<UserMini> findAllUserSupport(boolean recevied);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param recevied
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findSupportsList(boolean recevied, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Support consultSupport(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Support deleteSupport(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteSupports(List<String> lines) throws NotFoundException, InvalidResourceException;
	
}
