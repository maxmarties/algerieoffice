package com.rinitec.algerieoffice.services.admins.dashboard;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalAdmin;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IJournalAdminService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	JournalAdmin addJournalAdmin(OnJournalAdminEvent event);
	
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
	ElementsList findJournalList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteJournal(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteJournals(List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 */
	void deleteAllJournals();
	
}
