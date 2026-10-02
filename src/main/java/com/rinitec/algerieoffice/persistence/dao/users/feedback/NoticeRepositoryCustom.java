package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmNoticeLine;
import com.rinitec.algerieoffice.web.modal.company.communication.NoticeLine;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetNotice;
import com.rinitec.algerieoffice.web.modal.user.communication.UserNoticeLine;

public interface NoticeRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllNoticeCompanyCriteria(Long companyId, Integer filter, String search);
	
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
	List<NoticeLine> findAllNoticeCompanyCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllNoticeUserCriteria(Long userId, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<UserNoticeLine> findAllNoticeUserCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param rows
	 * @param page
	 * @return
	 */
	List<ExplorerWidgetNotice> findAllApprouvedNoticeCriteria(Long companyId, int rows, int page);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllNoticeAdmin(Integer filter, String search);
	
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
	List<AdmNoticeLine> findAllNoticeAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
