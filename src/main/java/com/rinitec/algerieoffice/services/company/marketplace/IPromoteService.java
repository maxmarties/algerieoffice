package com.rinitec.algerieoffice.services.company.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.marketplace.OrderForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.PromoteForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IPromoteService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countTrashedPromote(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean checkOrder(Long userId, OrderType type);

	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	PromoteForm readPromoteForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param promoteForm
	 * @param userId
	 * @return
	 */
	Promote addPromote(PromoteForm promoteForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param promoteForm
	 * @param userId
	 * @return
	 */
	Promote updatePromote(PromoteForm promoteForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorPromote(Long companyId);
	
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
	ElementsList findPromotesList(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Promote trashPromote(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void trashPromotes(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void trashAllPromotes(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	OrderForm readOrderForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param orderForm
	 * @param userId
	 * @return
	 */
	DocumentOrder updateDocumentOrder(OrderForm orderForm, Long userId);
	
}
