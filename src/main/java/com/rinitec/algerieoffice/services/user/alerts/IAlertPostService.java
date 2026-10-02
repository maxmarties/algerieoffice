package com.rinitec.algerieoffice.services.user.alerts;

import java.util.List;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.user.alerts.AlertPostForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostResult;

public interface IAlertPostService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param type
	 * @return
	 */
	AlertPostForm readAlertPostForm(String id, Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param alertPostForm
	 * @return
	 */
	AlertPost addAlertPost(AlertPostForm alertPostForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param alertPostForm
	 * @return
	 */
	AlertPost updateAlertPost(AlertPostForm alertPostForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @param type
	 * @return
	 */
	ElementsList findAlertPostList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAlertPost(String id, Long userId, DocumentType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAlertPosts(List<String> lines, Long userId, DocumentType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 */
	void deleteAllAlertPosts(Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param type
	 * @param day
	 * @param limit
	 * @return
	 */
	List<AlertPostResult> findLastAlertPost(DocumentType type, int day, int limit);
	
}
