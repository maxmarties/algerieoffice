package com.rinitec.algerieoffice.persistence.dao.companymaps;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmCampaignOrderLine;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmPromoteOrderLine;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumOrderLine;

public interface DocumentOrderRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllPromoteOrderAdmin(Boolean filter, String search);
	
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
	List<AdmPromoteOrderLine> findAllPromoteOrderAdmin(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllCampaignOrderAdmin(Boolean filter, String search);
	
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
	List<AdmCampaignOrderLine> findAllCampaignOrderAdmin(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param type
	 * @return
	 */
	Long countAllPremiumOrderAdmin(Integer filter, String search, OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @param type
	 * @return
	 */
	List<AdmPremiumOrderLine> findAllPremiumOrderAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc, OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param orderId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	AdmPremiumOrderLine findOneAdmPremiumOrderLine(UUID orderId, OrderType type) throws Exception;
	
}
