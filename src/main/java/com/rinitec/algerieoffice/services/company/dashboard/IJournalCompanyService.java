package com.rinitec.algerieoffice.services.company.dashboard;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalAccess;

public interface IJournalCompanyService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	JournalCompany addJournalCompany(OnJournalCompanyEvent event);
	
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
	ElementsList findJournalList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteJournal(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteJournals(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllJournals(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<JournalAccess> findJournalAccessList(Long companyId);
	
}
