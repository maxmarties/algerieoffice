package com.rinitec.algerieoffice.services.admins.blog;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogExplorerMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogHomeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMarketMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogSimultudeMini;

public interface IBlogSearchService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param keyword
	 * @param autorId
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findBlogExplorerList(Integer filter, String search, String keyword, Long autorId, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 * @param sort
	 * @param limit
	 * @return
	 */
	List<BlogMini> findLastBlogMini(String blogId, int sort, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 * @param category
	 * @param language
	 * @param keysword
	 * @param limit
	 * @return
	 */
	List<BlogSimultudeMini> findSimultudeBlogMini(String blogId, int category, String language, String keysword, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<BlogNewsMini> findBlogNewsMini(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	void incrementBlogFollow(String blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<BlogExplorerMini> findExplorerBlogMini(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	BlogMarketMini findOneBlogMarketMini();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<BlogMarketMini> findLastBlogMarketMini(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<BlogHomeMini> findLastBlogHomeMini(int limit);
	
}
