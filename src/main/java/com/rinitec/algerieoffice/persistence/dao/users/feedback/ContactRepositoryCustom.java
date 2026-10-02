package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmContactsLine;
import com.rinitec.algerieoffice.web.modal.company.communication.ContactLine;

public interface ContactRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllContactCriteria(Long companyId, Integer filter, String search);
	
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
	List<ContactLine> findAllContactCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countContactCompany(Long companyId, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param object
	 * @param begin
	 * @return
	 */
	Long countContactCompanyByObject(Long companyId, Integer object, DateTime begin);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllContactAdmin(Integer filter, String search);
	
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
	List<AdmContactsLine> findAllContactAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
