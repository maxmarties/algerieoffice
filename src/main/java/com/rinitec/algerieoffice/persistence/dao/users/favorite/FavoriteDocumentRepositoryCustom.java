package com.rinitec.algerieoffice.persistence.dao.users.favorite;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteAnnonceLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteEmployeLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteEventLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoritePostLine;

public interface FavoriteDocumentRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFavoritePostCriteria(Long userId, String filter, String search);
	
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
	List<FavoritePostLine> findAllFavoritePostCriteria(Long userId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFavoriteAnnonceCriteria(Long userId, Integer filter, String search);
	
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
	List<FavoriteAnnonceLine> findAllFavoriteAnnonceCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFavoriteEventCriteria(Long userId, Integer filter, String search);
	
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
	List<FavoriteEventLine> findAllFavoriteEventCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFavoriteEmployeCriteria(Long userId, Integer filter, String search);
	
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
	List<FavoriteEmployeLine> findAllFavoriteEmployeCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param filter
	 * @return
	 */
	Long countFavoriteDocumentByType(Long companyId, Integer type, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @return
	 */
	Long countFavoriteDocumentUserByType(Long userId, Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countFavoriteDocumentUser(Long userId, DateTime begin, DateTime end);
	
}
