package com.rinitec.algerieoffice.services.company.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.marketplace.CampaignForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.OrderForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface ICampaignService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllPostCampaignMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllAnnonceCampaignMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllEventCampaignMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param campaign
	 * @return
	 */
	String readCampaignTitle(Campaign campaign);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	CampaignForm readCampaignForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param campaignForm
	 * @param userId
	 * @return
	 */
	Campaign addCampaign(CampaignForm campaignForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param campaignForm
	 * @param userId
	 * @return
	 */
	Campaign updateCampaign(CampaignForm campaignForm, Long userId);
	
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
	ElementsList findPromotesList(Long companyId, Integer filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	String deleteCampaign(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteCampaigns(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllCampaigns(Long companyId);
	
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
