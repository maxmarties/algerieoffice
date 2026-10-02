package com.rinitec.algerieoffice.services.company.tools;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.tools.OrderPremiumForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface ISubscribeService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findPremiumList(Long companyId, Integer filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param id
	 * @throws NotFoundException
	 * @throws AccessUploadException
	 * @throws InvalidResourceException
	 */
	void deletePremium(Long companyId, String id) throws NotFoundException, AccessUploadException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deletePremiums(Long companyId, List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllPremiums(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @return
	 */
	OrderPremiumForm readOrderPremiumForm(Long userId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param orderPremiumForm
	 * @param userId
	 * @return
	 */
	DocumentOrder updateDocumentOrder(OrderPremiumForm orderPremiumForm, Long userId);
	
}
