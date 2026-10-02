package com.rinitec.algerieoffice.services.explorer;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCompany;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCurrent;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerElementsList;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPage404;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageAnnonces;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageContact;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageElements;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageHome;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePosts;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePresentation;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageTimeline;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetAgent;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetNotice;

public interface IExplorerService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyURL
	 * @return
	 */
	Long findExplorerCompanyId(String companyURL);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param url
	 * @param hasPreview
	 * @return
	 */
	ExplorerCurrent readExplorerCurrent(Long companyId, String url, boolean hasPreview);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @return
	 */
	ExplorerCompany readExplorerCompany(ExplorerCurrent explorerCurrent);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param display
	 * @return
	 */
	ExplorerPageHome readExplorerPageHome(ExplorerCurrent explorerCurrent, String display);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param rows
	 * @param page
	 * @return
	 */
	List<ExplorerWidgetAgent> findExplorerWidgetAgentList(Long companyId, int rows, int page);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param rows
	 * @param page
	 * @return
	 */
	List<ExplorerWidgetNotice> findExplorerWidgetNoticeList(Long companyId, int rows, int page);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param display
	 * @return
	 */
	ExplorerPagePresentation readExplorerPagePresentation(Long companyId, String display);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param display
	 * @return
	 */
	ExplorerPageTimeline readExplorerPageTimeline(Long companyId, String display);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param display
	 * @return
	 */
	ExplorerPageElements readExplorerPageElements(Long companyId, String display);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param explorerCurrent
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxActus(Long userId, ExplorerCurrent explorerCurrent, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxEvents(ExplorerCurrent explorerCurrent, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxWorks(ExplorerCurrent explorerCurrent, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxFaqs(ExplorerCurrent explorerCurrent, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxPartners(ExplorerCurrent explorerCurrent, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param display
	 * @return
	 */
	ExplorerPagePosts readExplorerPagePosts(Long companyId, String display);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	UUIDMini findExplorerCategory(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxPosts(ExplorerCurrent explorerCurrent, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param display
	 * @return
	 */
	ExplorerPageAnnonces readExplorerPageAnnonces(Long companyId, String display);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param type
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxAnnonces(ExplorerCurrent explorerCurrent, String type, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ExplorerElementsList readExplorerInboxEmployes(ExplorerCurrent explorerCurrent, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	ExplorerPageContact readExplorerPageContact(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	ExplorerPage404 readExplorerPage404(Long companyId);
	
}
