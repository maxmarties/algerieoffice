package com.rinitec.algerieoffice.services.analytic;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.analytic.AccessCompany;
import com.rinitec.algerieoffice.web.listener.events.OnAccessCompanyEvent;

public interface IAccessService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	AccessCompany addAccessCompany(OnAccessCompanyEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 */
	void incrementClickDocument(String documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 */
	void incrementWorkDocument(String documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	void incrementViewBlog(String blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	void incrementMarketBlog(String blogId);
	
}
