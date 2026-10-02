package com.rinitec.algerieoffice.services.user.dashboard;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalUser;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardHistory;

public interface IJournalUserService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	JournalUser addJournalUser(OnJournalUserEvent event);
	
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
	ElementsList findJournalList(Long userId, Integer filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteJournal(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteJournals(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllJournals(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<DashboardHistory> findLastDashboardHistory(Long userId, int limit);
	
}
