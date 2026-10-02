package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.modal.admins.blog.BlogLine;
import com.rinitec.algerieoffice.web.modal.admins.blog.BlogStatLine;
import com.rinitec.algerieoffice.web.modal.mapsite.BlogMapsite;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogExplorerMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogHomeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMarketMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogWidgetMini;

public interface BlogRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllBlogCriteria(Integer filter, String search);
	
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
	List<BlogLine> findAllBlogCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllBlogStatsCriteria(Integer filter, String search);
	
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
	List<BlogStatLine> findAllBlogStatsCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param keyword
	 * @param autorId
	 * @return
	 */
	Long countAllBlogExplorer(Integer filter, String search, String keyword, Long autorId);
	
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
	List<BlogWidgetMini> findAllBlogExplorer(Integer filter, String search, String keyword, Long autorId, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 * @param sort
	 * @param limit
	 * @return
	 */
	List<BlogMini> findLastBlogMini(UUID blogId, int sort, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 * @param category
	 * @param language
	 * @param keysword
	 * @param limit
	 * @return
	 */
	List<BlogSimultudeMini> findSimultudeBlogMini(UUID blogId, int category, String language, String keysword, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<BlogNewsMini> findLastBlogNewsMini(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<BlogExplorerMini> findLastExplorerBlogMini(int limit);
	
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
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllBlogMapsite();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<BlogMapsite> findAllBlogMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<UUIDMini> findLastBlogNewsletter(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	List<BlogNewsletterMini> findAllBlogNewsletterMini(List<UUID> lines);
	
}
