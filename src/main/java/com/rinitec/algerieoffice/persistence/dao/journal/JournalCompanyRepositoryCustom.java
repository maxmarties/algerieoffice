package com.rinitec.algerieoffice.persistence.dao.journal;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardJournal;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalLine;

public interface JournalCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllJournalCompanyCriteria(Long companyId, Integer filter, String search);

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
	List<JournalLine> findAllJouranlCompanyCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param action
	 * @return
	 */
	List<JournalAccess> findJournalAccessListCriteria(Long companyId, String action);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param login
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countJournalCompany(Long companyId, Boolean login, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DashboardJournal> findLastJouranlCompany(Long companyId, int limit);
	
}
