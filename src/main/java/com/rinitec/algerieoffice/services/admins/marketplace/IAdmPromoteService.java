package com.rinitec.algerieoffice.services.admins.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmPromoteForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmOrderPromote;

public interface IAdmPromoteService {

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
	ElementsList findAdmPromotesList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmPromoteForm readAdmPromoteForm(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admPromoteForm
	 * @return
	 */
	Promote moderatePromote(AdmPromoteForm admPromoteForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Promote deletePromote(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deletePromotes(List<String> lines) throws NotFoundException, InvalidResourceException;
	
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
	ElementsList findAdmPromoteOrdersList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmOrderPromote readAdmOrderPromote(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param adminId
	 * @param admOrderForm
	 * @return
	 * @throws AlreadyExistException
	 */
	Object[] validateOrder(Long adminId, AdmOrderForm admOrderForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteOrder(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteOrders(List<String> lines) throws NotFoundException, InvalidResourceException;
	
}
