package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.result.EventMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.portfolio.EventLine;
import com.rinitec.algerieoffice.web.modal.mapsite.EventMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventWidgetMini;

public interface EventRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEventCriteria(Long companyId, Long filter, String search);
	
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
	List<EventLine> findAllEventCriteria(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
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
	Long countAllExplorerEventCriteria(Long companyId, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EventMini> findAllExplorerEventCriteria(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEventForm
	 * @return
	 */
	Long countAllEventWidget(SearchEventForm searchEventForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEventForm
	 * @return
	 */
	List<EventWidgetMini> findEventWidgetList(SearchEventForm searchEventForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEventForm
	 * @return
	 */
	List<UUID> findAllEventEasylist(SearchEventForm searchEventForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @param wilayas
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<EventSimultudeMini> findEventProxisList(Event event, List<Integer> wilayas, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<EventSimultudeMini> findEventSourcesList(UUID eventId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllEventCampaignMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * USED FOR MARKETPLACE AND MAPSITE
	 * @return
	 */
	Long countAllActiveEvent();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<EventMapsite> findAllPostMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param published
	 * @return
	 */
	Long countAllEvent(Integer filter, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	List<NewsletterItem> findAllNewsletterItem(Long companyId, List<UUID> lines);
	
}
