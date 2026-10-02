package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.realtime.DeactivateCompanyLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.DeactivateUserLine;

public interface DeactivateRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllDeactivateCompany(Integer filter, String search);
	
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
	List<DeactivateCompanyLine> findAllDeactivateCompany(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllDeactivateUser(Integer filter, String search);
	
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
	List<DeactivateUserLine> findAllDeactivateUser(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
