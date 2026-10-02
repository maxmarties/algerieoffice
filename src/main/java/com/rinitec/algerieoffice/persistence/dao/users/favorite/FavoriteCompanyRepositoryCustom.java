package com.rinitec.algerieoffice.persistence.dao.users.favorite;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedCompany;
import com.rinitec.algerieoffice.web.modal.user.globe.FavoriteCompanyLine;

public interface FavoriteCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFavoriteCompanyCriteria(Long userId, Integer filter, String search);
	
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
	List<FavoriteCompanyLine> findAllFavoriteCompanyCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllFavoriteUserCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FollowedCompany> findAllFollowedCompany(Long userId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param filter
	 * @return
	 */
	Long countFavoriteCompanyByType(Long companyId, Integer type, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	Long countFavoriteCompanyAlerte(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countFavoriteCompany(Long companyId, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllNewsletterUserCompany(Long companyId);
	
}
