package com.rinitec.algerieoffice.persistence.dao.users.profiles;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmBlockLine;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmRateLine;

public interface RateRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllRateAdmin(Integer filter, String search);
	
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
	List<AdmRateLine> findAllRateAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @return
	 */
	Long countAllBlockAdmin(String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmBlockLine> findAllBlockAdmin(String search, int sort, int rows, int page, boolean hasDesc);
	
}
