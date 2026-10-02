package com.rinitec.algerieoffice.persistence.dao.users.favorite;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedAccount;
import com.rinitec.algerieoffice.web.modal.user.globe.FavoriteAccountLine;

public interface FavoriteAccountRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFavoriteAccountCriteria(Long userId, String filter, String search);
	
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
	List<FavoriteAccountLine> findAllFavoriteAccountCriteria(Long userId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FollowedAccount> findAllFollowedAccount(Long userId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param accountId
	 * @return
	 */
	List<UserMini> findAllFavoriteUser(Long accountId);
	
}
