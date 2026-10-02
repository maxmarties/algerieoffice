package com.rinitec.algerieoffice.services.company.posts;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.posts.CategoryForm;
import com.rinitec.algerieoffice.web.form.company.posts.PostForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.posts.AnalyticPost;
import com.rinitec.algerieoffice.web.modal.company.posts.PostAccess;

public interface IPostService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countPost(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countTrashedPost(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	PostForm readPostForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws NotFoundException
	 * @throws MaxKeyswordException
	 * @throws EmptyElementException
	 */
	Post addPost(PostForm postForm, int maxKeysword, Long userId) throws AlreadyExistException, UrlUnavailableException, NotFoundException, 
		MaxKeyswordException, EmptyElementException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws NotFoundException
	 * @throws MaxKeyswordException
	 * @throws EmptyElementException
	 */
	Post updatePost(PostForm postForm, int maxKeysword, Long userId) throws AlreadyExistException, UrlUnavailableException, NotFoundException, 
		MaxKeyswordException, EmptyElementException;
	
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
	ElementsList findPostsList(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Post trashPost(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void trashPosts(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void trashAllPosts(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	List<UUIDMini> findAllChoseCategory(Long companyId, String categoryId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllFilterCategory(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllCategory(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	CategoryForm readCategoryForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param categoryForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws NotFoundException
	 */
	Category addCategory(CategoryForm categoryForm) throws AlreadyExistException, UrlUnavailableException, NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param categoryForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws NotFoundException
	 */
	Category updateCategory(CategoryForm categoryForm) throws AlreadyExistException, UrlUnavailableException, NotFoundException;
	
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
	ElementsList findCategoriesList(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Category deleteCategory(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteCategories(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllCategories(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticPost readAnalyticPost(Long companyId);
	
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
	ElementsList findPostStatsList(Long companyId, Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<PostAccess> findPostAccessList(Long companyId, int limit);
	
}
