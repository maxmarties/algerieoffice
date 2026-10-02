package com.rinitec.algerieoffice.services.admins.blog;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.admins.blog.BlogForm;
import com.rinitec.algerieoffice.web.form.admins.blog.BlogStatForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogAutorMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogInbox;

public interface IBlogService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	boolean existsByIdentify(String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	BlogForm readBlogForm(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogForm
	 * @return
	 * @throws UrlUnavailableException
	 * @throws NotFoundException
	 * @throws MaxKeyswordException
	 */
	Blog addBlog(BlogForm blogForm) throws UrlUnavailableException, NotFoundException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogForm
	 * @return
	 * @throws UrlUnavailableException
	 * @throws NotFoundException
	 * @throws MaxKeyswordException
	 */
	Blog updateBlog(BlogForm blogForm) throws UrlUnavailableException, NotFoundException, MaxKeyswordException;
	
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
	ElementsList findBlogsList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Blog deleteBlog(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteBlogs(List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<String> countFamilyBlog();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	BlogAutorMini readAutor(final String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	BlogInbox readBlogInbox(final String identify);
	
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
	ElementsList findBlogStatsList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	BlogStatForm readBlogStatForm(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogStatForm
	 * @return
	 */
	Blog updateBlogStat(BlogStatForm blogStatForm);
	
}
