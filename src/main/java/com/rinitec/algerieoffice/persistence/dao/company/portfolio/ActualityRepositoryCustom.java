package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.result.ActualityMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.form.search.SearchNewsForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.portfolio.ActualityLine;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetActuality;
import com.rinitec.algerieoffice.web.modal.mapsite.ActualityMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.ScreenInboxNews;

public interface ActualityRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllActualityCriteria(Long companyId, Long filter, String search);
	
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
	List<ActualityLine> findAllActualityCriteria(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorCriteria(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @return
	 */
	Long countAllExplorerActualityCriteria(Long companyId, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<ExplorerWidgetActuality> findAllExplorerActualityCriteria(Long userId, Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<ActualityMini> findLastExplorerActuality(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchNewsForm
	 * @return
	 */
	Long countAllNewsWidget(SearchNewsForm searchNewsForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchNewsForm
	 * @return
	 */
	List<NewsWidgetMini> findNewsWidgetList(SearchNewsForm searchNewsForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actuId
	 * @return
	 * @throws Exception
	 */
	ScreenInboxNews findOneScreenInboxNews(UUID actuId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActyalityMapsite();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<ActualityMapsite> findAllActualityMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param published
	 * @return
	 */
	Long countAllActuality(Integer filter, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllActualityNewsletterMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	List<NewsletterItem> findAllNewsletterItem(Long companyId, List<UUID> lines);
	
}
