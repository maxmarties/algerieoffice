package com.rinitec.algerieoffice.services.admins.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmCampaignForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmOrderCampagne;

public interface IAdmCampaignService {

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
	ElementsList findAdmCampaignsList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmCampaignForm readAdmCampaignForm(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admCampaignForm
	 * @return
	 */
	Campaign updateCampaign(AdmCampaignForm admCampaignForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Campaign deleteCampaign(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteCampaigns(List<String> lines) throws NotFoundException, InvalidResourceException;
	
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
	ElementsList findAdmCampaignOrdersList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmOrderCampagne readAdmOrderCampagne(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param adminId
	 * @param admOrderForm
	 * @return
	 * @throws AlreadyExistException
	 */
	Long validateOrder(Long adminId, AdmOrderForm admOrderForm) throws AlreadyExistException;
	
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
