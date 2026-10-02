package com.rinitec.algerieoffice.persistence.dao.journal;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardHistory;
import com.rinitec.algerieoffice.web.modal.user.dashboard.HistoryLine;

public interface JournalUserRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @return
	 */
	Long countAllJournalUserCriteria(Long userId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<HistoryLine> findAllJouranlUserCriteria(Long userId, Integer filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<DashboardHistory> findLastDashboardHistory(Long userId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countJournalUser(Long userId, DateTime begin, DateTime end);
	
}
