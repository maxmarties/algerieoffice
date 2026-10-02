package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmLockLine;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmReportLine;

public interface ReportRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllReportAdmin(Integer filter, String search);
	
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
	List<AdmReportLine> findAllReportAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @return
	 */
	Long countAllLockAdmin(String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmLockLine> findAllLockAdmin(String search, int sort, int rows, int page, boolean hasDesc);
	
}
