package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletter;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmAnnonceLine;
import com.rinitec.algerieoffice.web.modal.company.marketplace.AnnonceLine;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.tools.RecycleAnnonceLine;
import com.rinitec.algerieoffice.web.modal.mapsite.AnnonceMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnonceSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnonceWidgetMini;

public interface AnnonceRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAnnonceCriteria(Long companyId, Integer filter, String search);
	
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
	List<AnnonceLine> findAllAnnonceCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<PostMini> findAllPublishedAnnonce(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllExplorerAnnonceCriteria(Long companyId, Integer filter, String search);
	
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
	List<Object[]> findAllExplorerAnnonceCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchAnnonceForm
	 * @return
	 */
	Long countAllAnnonceWidget(SearchAnnonceForm searchAnnonceForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchAnnonceForm
	 * @return
	 */
	List<AnnonceWidgetMini> findAnnonceWidgetList(SearchAnnonceForm searchAnnonceForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchAnnonceForm
	 * @return
	 */
	List<UUID> findAllAnnonceEasylist(SearchAnnonceForm searchAnnonceForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonce
	 * @param sectors
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<AnnonceSimultudeMini> findAnnonceProxisList(Annonce annonce, List<Integer> sectors, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<AnnonceSimultudeMini> findAnnonceSourcesList(UUID annonceId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllAnnonceCampaignMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmAnnonceCriteria(Integer filter, String search);
	
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
	List<AdmAnnonceLine> findAllAdmAnnonceCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllRecycleAnnonceCriteria(Long companyId, Integer filter, String search);
	
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
	List<RecycleAnnonceLine> findAllRecycleAnnonceCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	Long countPublishedAnnonce(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * USED FOR MARKETPLACE AND MAPSITE
	 * @return
	 */
	Long countAllActiveAnnonce();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<AnnonceMapsite> findAllPostMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<AnnonceNewsletter> findAllAnnonceNewsletter(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param begin
	 * @param end
	 * @param type
	 * @return
	 */
	Long countAllNewsAnnonce(DateTime begin, DateTime end, Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	List<AnnonceNewsletterMini> findAllAnnonceNewsletterMini(List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param published
	 * @return
	 */
	Long countAllAnnonce(Integer filter, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	List<NewsletterItem> findAllNewsletterItem(Long companyId, List<UUID> lines);
	
}
