package com.rinitec.algerieoffice.services.publics;

import com.rinitec.algerieoffice.web.modal.publics.CompanyAutorMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentsSimultudeList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.ScreenInboxAnnonce;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.ScreenInboxEmploye;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.ScreenInboxEvent;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.ScreenInboxNews;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.ScreenInboxPost;

public interface ISearchDetailService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyURL
	 * @return
	 */
	CompanyAutorMini findCompanyAutorMini(String companyURL);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param postURL
	 * @return
	 */
	ScreenInboxPost readScreenInboxPost(Long companyId, String postURL);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	DocumentsSimultudeList findPostsSimilarList(String postId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param annonceURL
	 * @return
	 */
	ScreenInboxAnnonce readScreenInboxAnnonce(Long companyId, String annonceURL);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	DocumentsSimultudeList findAnnoncesSimultudeList(String annonceId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param employeURL
	 * @return
	 */
	ScreenInboxEmploye readScreenInboxEmploye(Long companyId, String employeURL);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	DocumentsSimultudeList findEmployesSimultudeList(String employeId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param eventURL
	 * @return
	 */
	ScreenInboxEvent readScreenInboxEvent(Long companyId, String eventURL);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	DocumentsSimultudeList findEventsSimultudeList(String eventId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actuId
	 * @return
	 */
	ScreenInboxNews findScreenInboxNews(String actuId);
	
}
