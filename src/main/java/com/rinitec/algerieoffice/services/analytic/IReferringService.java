package com.rinitec.algerieoffice.services.analytic;

import com.rinitec.algerieoffice.persistence.modal.analytic.FollowCompany;
import com.rinitec.algerieoffice.persistence.modal.analytic.Outlook;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompletedEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringPostEvent;

public interface IReferringService {
	public static final int GET_PHONE = 1;
	public static final int APP_PHONE = 2;
	public static final int SEND_MAIL = 3;

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param out
	 * @return
	 */
	Outlook postOrIncrementOutlook(Long companyId, int out);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param out
	 * @return
	 */
	FollowCompany postOrIncrementFollowCompany(Long companyId, String out);

	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 */
	void incrementReferringCompanies(OnReferringCompanyEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 */
	void incrementReferringPosts(OnReferringPostEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 */
	void updateReferringCompleted(OnReferringCompletedEvent event);
	
}
