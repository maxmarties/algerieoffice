package com.rinitec.algerieoffice.persistence.dao.company.team;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.team.GuestLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserContributorLine;

public interface GuestRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllGuestCompanyCriteria(Long companyId, Integer filter, String search);
	
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
	List<GuestLine> findAllGuestCompanyCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllGuestUserCriteria(Long userId, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<UserContributorLine> findAllGuestUserCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
