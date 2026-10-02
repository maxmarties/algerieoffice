package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsCommentLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserCommentLine;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardComment;

public interface ActualityCommentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actuId
	 * @return
	 */
	List<UserMini> findAllUsersComment(Long userId, UUID actuId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actuId
	 * @return
	 */
	Long countAllActualityComments(UUID actuId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actuId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<NewsCommentLine> findAllActualityComments(Long userId, UUID actuId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllActualityCommentCriteria(Long userId, Integer filter, String search);
	
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
	List<UserCommentLine> findAllActualityCommentCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<DashboardComment> findLastDashboardComment(Long userId, int limit);
	
}
