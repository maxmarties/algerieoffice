package com.rinitec.algerieoffice.persistence.dao.journal;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmJournalLine;

public interface JournalAdminRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllJournalAdminCriteria(Integer filter, String search);
	
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
	List<AdmJournalLine> findAllJouranlAdminCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countJournalAdmin(DateTime begin, DateTime end);
	
}
