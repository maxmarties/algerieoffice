package com.rinitec.algerieoffice.services.explorer;

import com.rinitec.algerieoffice.web.modal.feedback.CampaignFeedback;
import com.rinitec.algerieoffice.web.modal.feedback.PromoteFeedback;
import com.rinitec.algerieoffice.web.modal.feedback.SponsoreFeedback;

public interface IExplorerPromoteService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	PromoteFeedback readPromoteFeedback(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	PromoteFeedback readPromoteFeedbackHome();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 */
	void incrementClickPromote(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param type
	 * @return
	 */
	SponsoreFeedback readSponsoreFeedback(Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 */
	void incrementClickSponsore(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CampaignFeedback readCampaignFeedback(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 */
	void incrementClickCampaign(String id);
	
}
