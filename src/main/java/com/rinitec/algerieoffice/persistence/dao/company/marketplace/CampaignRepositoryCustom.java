package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmCampaignLine;

public interface CampaignRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	Long countAllCampaignCriteria(Long companyId, Integer filter);
	
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
	List<Object[]> findAllCampaignCriteria(Long companyId, Integer filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllCampaignAdmin(Integer filter, String search);
	
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
	List<AdmCampaignLine> findAllCampaignAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	Campaign findCampaignDashboard(Long companyId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActiveCampaign();
	
}
